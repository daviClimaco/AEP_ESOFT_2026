const STATUS_MAP = {
  AGUARDANDO_ANALISE: { label: 'Aguardando', cls: 'badge-waiting'  },
  EM_ANALISE:         { label: 'Em análise', cls: 'badge-analysis' },
  APROVADA:           { label: 'Aprovada',   cls: 'badge-approved' },
  INDEFERIDA:         { label: 'Indeferida', cls: 'badge-priority' },
};

const PRIO_MAP = {
  ALTA:   { label: 'Alta',   cls: 'badge-priority' },
  MEDIA:  { label: 'Média',  cls: 'badge-medium'   },
  NORMAL: { label: 'Normal', cls: 'badge-normal'   },
};

const TURNO_MAP = { MANHA: 'Manhã', TARDE: 'Tarde', INTEGRAL: 'Integral' };

let todasSolicitacoes = [];
let solicitacaoAtual  = null;

async function carregarTabela() {
  const status = document.getElementById('filtro-status').value;
  const url    = '/api/solicitacoes' + (status ? '?status=' + status : '');

  document.getElementById('tbody').innerHTML =
    '<tr><td colspan="7" style="text-align:center;padding:24px;color:var(--gray-400)">' +
    '<span class="spinner" style="border-top-color:var(--blue-500);border-color:var(--gray-200)"></span>' +
    '</td></tr>';

  try {
    const res = await fetch(url);
    todasSolicitacoes = await res.json();
    renderTabela(todasSolicitacoes);
  } catch (e) {
    showAlert('error', 'Erro ao carregar solicitações.');
  }
}

function filtrar() {
  const q = document.getElementById('filtro-busca').value.toLowerCase();
  renderTabela(
    todasSolicitacoes.filter(s =>
      (s.responsavel?.nome  || '').toLowerCase().includes(q) ||
      (s.protocolo          || '').toLowerCase().includes(q)
    )
  );
}

function renderTabela(lista) {
  const tbody = document.getElementById('tbody');

  if (!lista.length) {
    tbody.innerHTML =
      '<tr><td colspan="7" style="text-align:center;padding:28px;color:var(--gray-400)">' +
      '<i class="ti ti-inbox" style="font-size:24px;display:block;margin-bottom:6px"></i>' +
      'Nenhuma solicitação encontrada.</td></tr>';
    document.getElementById('table-footer').textContent = '0 solicitações';
    return;
  }

  tbody.innerHTML = lista.map(s => {
    const st = STATUS_MAP[s.status]    || { label: s.status,      cls: 'badge-analysis' };
    const pr = PRIO_MAP[s.prioridade]  || { label: s.prioridade || '—', cls: 'badge-analysis' };
    return `
      <tr>
        <td><b>${s.protocolo || '—'}</b></td>
        <td>${s.responsavel?.nome || '—'}</td>
        <td>${s.crianca?.nome || '—'}, ${s.crianca?.idadeAnos || '?'} ano(s)</td>
        <td>${s.crechePreferencia || '—'}</td>
        <td><span class="badge ${pr.cls}">${pr.label}</span></td>
        <td><span class="badge ${st.cls}">${st.label}</span></td>
        <td>
          <button class="action-btn" onclick="abrirModal(${s.id})" title="Ver detalhes">
            <i class="ti ti-eye"></i> Ver
          </button>
        </td>
      </tr>`;
  }).join('');

  document.getElementById('table-footer').textContent =
    lista.length + ' solicitação(ões) exibida(s)';
}

async function abrirModal(id) {
  try {
    const res = await fetch('/api/solicitacoes/' + id);
    const s   = await res.json();
    solicitacaoAtual = s;
    preencherModal(s);
    document.getElementById('modal-overlay').classList.add('open');
    document.querySelector('.modal-close').focus();
  } catch (e) {
    showAlert('error', 'Erro ao carregar detalhes.');
  }
}

function preencherModal(s) {
  const st = STATUS_MAP[s.status] || { label: s.status, cls: 'badge-analysis' };
  document.getElementById('modal-titulo').textContent =
    'Solicitação ' + (s.protocolo || '') + ' — ' + st.label;

  document.getElementById('d-resp-nome').textContent  = s.responsavel?.nome     || '—';
  document.getElementById('d-resp-cpf').textContent   = s.responsavel?.cpf      || '—';
  document.getElementById('d-resp-tel').textContent   = s.responsavel?.telefone || '—';
  document.getElementById('d-resp-email').textContent = s.responsavel?.email    || '—';
  document.getElementById('d-resp-end').textContent   = s.responsavel?.endereco || '—';

  document.getElementById('d-crianca-nome').textContent  = s.crianca?.nome       || '—';
  document.getElementById('d-crianca-idade').textContent = (s.crianca?.idadeAnos || '?') + ' ano(s)';
  document.getElementById('d-creche').textContent        = s.crechePreferencia   || '—';
  document.getElementById('d-turno').textContent         = TURNO_MAP[s.turno] || s.turno || '—';
  document.getElementById('d-posicao').textContent       = s.posicaoNaFila
    ? s.posicaoNaFila + 'º na fila' : 'Fora da fila';

  const criterios = [
    ['Vulnerabilidade social',        s.vulnerabilidadeSocial],
    ['Mãe/responsável trabalhador(a)', s.maeTrabalhadora],
    ['Renda per capita baixa',         s.rendaPerCapitaBaixa],
    ['Família monoparental',           s.familiaMonoparental],
  ];
  document.getElementById('d-criterios').innerHTML = criterios
    .map(([label, val]) =>
      `<span class="criterio-tag ${val ? 'criterio-sim' : 'criterio-nao'}">
        <i class="ti ti-${val ? 'check' : 'x'}"></i> ${label}
      </span>`
    ).join('');

  const justWrap = document.getElementById('d-justificativa-wrap');
  if (s.status === 'INDEFERIDA' && s.justificativa) {
    justWrap.hidden = false;
    document.getElementById('d-justificativa-texto').textContent = s.justificativa;
  } else {
    justWrap.hidden = true;
  }

  document.getElementById('d-historico').innerHTML =
    (s.historicoStatus || []).length
      ? (s.historicoStatus).map(h => `<div class="tl-mini">${h}</div>`).join('')
      : '<p style="font-size:13px;color:var(--gray-400)">Sem histórico.</p>';

  document.getElementById('indeferir-section').style.display = 'none';
  document.getElementById('motivo-indeferimento').value = '';

  renderBotoesModal(s);
}

function renderBotoesModal(s) {
  const actions      = document.getElementById('modal-actions');
  const podeAnalisar = s.status === 'AGUARDANDO_ANALISE';
  const podeAprovar  = s.status === 'AGUARDANDO_ANALISE' || s.status === 'EM_ANALISE';
  const podeIndeferir= s.status === 'AGUARDANDO_ANALISE' || s.status === 'EM_ANALISE';

  actions.innerHTML = '';

  if (podeAnalisar) {
    const b = document.createElement('button');
    b.className = 'btn-outline';
    b.innerHTML = '<i class="ti ti-search"></i> Iniciar análise';
    b.onclick   = () => executarAcao('analisar');
    actions.appendChild(b);
  }

  if (podeAprovar) {
    const b = document.createElement('button');
    b.className = 'btn-primary';
    b.innerHTML = '<i class="ti ti-check"></i> Aprovar';
    b.onclick   = () => executarAcao('aprovar');
    actions.appendChild(b);
  }

  if (podeIndeferir) {
    const b = document.createElement('button');
    b.className = 'btn-outline';
    b.style.cssText = 'color:var(--red-text);border-color:var(--red-text)';
    b.innerHTML = '<i class="ti ti-x"></i> Indeferir';
    b.onclick   = mostrarCampoIndeferimento;
    actions.appendChild(b);
  }

  if (!podeAnalisar && !podeAprovar) {
    const span = document.createElement('span');
    span.style.cssText = 'font-size:13px;color:var(--gray-400)';
    span.textContent   = 'Solicitação já concluída.';
    actions.appendChild(span);
  }
}

function mostrarCampoIndeferimento() {
  document.getElementById('indeferir-section').style.display = 'block';
  document.getElementById('motivo-indeferimento').focus();
}

function cancelarIndeferimento() {
  document.getElementById('indeferir-section').style.display = 'none';
  document.getElementById('motivo-indeferimento').value = '';
}

async function confirmarIndeferimento() {
  const textarea = document.getElementById('motivo-indeferimento');
  const motivo   = textarea.value.trim();

  if (!motivo) {
    textarea.style.borderColor = 'var(--red-text)';
    textarea.focus();
    return;
  }

  textarea.style.borderColor = '';

  await fetch('/api/solicitacoes/' + solicitacaoAtual.id + '/indeferir', {
    method: 'PATCH',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ justificativa: motivo }),
  });

  showAlert('success', 'Solicitação indeferida com sucesso.');
  fecharModalDireto();
  carregarTabela();
}

async function executarAcao(acao) {
  await fetch('/api/solicitacoes/' + solicitacaoAtual.id + '/' + acao, { method: 'PATCH' });
  showAlert('success', 'Ação realizada com sucesso!');
  fecharModalDireto();
  carregarTabela();
}

function fecharModal(e) {
  if (e.target === document.getElementById('modal-overlay')) fecharModalDireto();
}

function fecharModalDireto() {
  document.getElementById('modal-overlay').classList.remove('open');
  solicitacaoAtual = null;
}

function showAlert(type, msg) {
  const box = document.getElementById('alert-box');
  box.className = `alert alert-${type === 'error' ? 'error' : 'success'} show`;
  box.innerHTML = `<i class="ti ti-${type === 'error' ? 'alert-circle' : 'circle-check'}"></i> ${msg}`;
  setTimeout(() => { box.className = 'alert'; }, 3500);
}

document.addEventListener('keydown', e => {
  if (e.key === 'Escape') fecharModalDireto();
});

carregarTabela();
