const STATUS_MAP = {
  AGUARDANDO_ANALISE: { label: 'Aguardando análise', cls: 'badge-waiting'  },
  EM_ANALISE:         { label: 'Em análise',         cls: 'badge-analysis' },
  APROVADA:           { label: 'Aprovada',           cls: 'badge-approved' },
  INDEFERIDA:         { label: 'Indeferida',         cls: 'badge-priority' },
};

document.getElementById('input-protocolo').addEventListener('keydown', e => {
  if (e.key === 'Enter') buscarProtocolo();
});

async function buscarProtocolo() {
  const proto = document.getElementById('input-protocolo').value.trim();
  if (!proto) return;

  const btn = document.querySelector('.search-row .btn-primary');
  btn.innerHTML = '<span class="spinner"></span>';
  btn.disabled = true;

  try {
    const res = await fetch('/api/solicitacoes/protocolo/' + encodeURIComponent(proto));
    if (!res.ok) {
      showAlert('error', 'Protocolo não encontrado. Verifique e tente novamente.');
      return;
    }
    const data = await res.json();
    renderCard(data);
  } catch (e) {
    showAlert('error', 'Erro de conexão com o servidor.');
  } finally {
    btn.innerHTML = '<i class="ti ti-search"></i> Buscar';
    btn.disabled = false;
  }
}

function renderCard(d) {
  document.getElementById('s-nome').textContent =
    d.crianca.nome + ' — ' + d.crianca.idadeAnos + ' ano(s)';

  document.getElementById('s-protocolo').textContent =
    'Protocolo nº ' + d.protocolo + ' · Enviado em ' +
    new Date(d.dataCriacao).toLocaleDateString('pt-BR');

  const st = STATUS_MAP[d.status] || { label: d.status, cls: 'badge-analysis' };
  const badge = document.getElementById('s-badge');
  badge.textContent = st.label;
  badge.className   = 'badge ' + st.cls;

  document.getElementById('s-posicao').textContent =
    d.posicaoNaFila ? d.posicaoNaFila + 'º' : '—';

  document.getElementById('s-creche').textContent =
    d.crechePreferencia + ' · Turno ' + d.turno;

  const criterios = [];
  if (d.vulnerabilidadeSocial) criterios.push(['Vulnerabilidade social', 'badge-priority']);
  if (d.maeTrabalhadora)       criterios.push(['Mãe trabalhadora',       'badge-analysis']);
  if (d.rendaPerCapitaBaixa)   criterios.push(['Renda per capita baixa', 'badge-medium']);
  if (d.familiaMonoparental)   criterios.push(['Família monoparental',   'badge-normal']);

  document.getElementById('s-criterios').innerHTML = criterios.length
    ? criterios.map(([t, c]) => `<span class="badge ${c}">${t}</span>`).join('')
    : '<span style="font-size:13px;color:var(--gray-400)">Nenhum critério especial</span>';

  document.getElementById('s-historico').innerHTML =
    (d.historicoStatus || []).length
      ? (d.historicoStatus).map(h =>
          `<div class="tl-item"><div class="tl-dot done"></div><div>${h}</div></div>`
        ).join('')
      : '<p style="font-size:13px;color:var(--gray-400)">Sem histórico ainda.</p>';

  document.getElementById('status-card').style.display = 'block';
  document.getElementById('btn-limpar').style.display   = 'inline-flex';
  document.getElementById('alert-box').className = 'alert';
}

function limparBusca() {
  document.getElementById('input-protocolo').value = '';
  document.getElementById('status-card').style.display = 'none';
  document.getElementById('btn-limpar').style.display  = 'none';
  document.getElementById('alert-box').className = 'alert';
  document.getElementById('input-protocolo').focus();
}

function showAlert(type, msg) {
  const box = document.getElementById('alert-box');
  box.className = `alert alert-${type === 'error' ? 'error' : 'success'} show`;
  box.textContent = msg;
  document.getElementById('status-card').style.display = 'none';
  document.getElementById('btn-limpar').style.display  = 'none';
}
