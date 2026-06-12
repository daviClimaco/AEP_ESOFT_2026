const ETAPAS = [null, 'etapa-1', 'etapa-2', 'etapa-3', 'etapa-4'];
const stepItems = document.querySelectorAll('.stepper-item');

function irEtapa(n) {
  ETAPAS.forEach((id, i) => {
    if (!id) return;
    const el = document.getElementById(id);
    if (i < n) {
      el.hidden = true;
      stepItems[i - 1]?.classList.remove('active');
      stepItems[i - 1]?.classList.add('done');
    } else if (i === n) {
      el.hidden = false;
      stepItems[i - 1]?.classList.add('active');
      stepItems[i - 1]?.classList.remove('done');
    } else {
      el.hidden = true;
      stepItems[i - 1]?.classList.remove('active', 'done');
    }
  });

  if (n === 4) gerarResumo();
  window.scrollTo({ top: 0, behavior: 'smooth' });
}

function gerarResumo() {
  const turnoMap = { MANHA: 'Manhã', TARDE: 'Tarde', INTEGRAL: 'Integral' };
  const criterios = [];
  if (document.getElementById('vulnerabilidadeSocial').checked) criterios.push('Vulnerabilidade social');
  if (document.getElementById('maeTrabalhadora').checked)       criterios.push('Mãe/responsável trabalhador(a)');
  if (document.getElementById('rendaPerCapitaBaixa').checked)   criterios.push('Renda per capita baixa');
  if (document.getElementById('familiaMonoparental').checked)   criterios.push('Família monoparental');

  const rows = [
    ['Responsável',  document.getElementById('nomeResponsavel').value],
    ['CPF',          document.getElementById('cpfResponsavel').value],
    ['Telefone',     document.getElementById('telefoneResponsavel').value],
    ['Criança',      `${document.getElementById('nomeCrianca').value}, ${document.getElementById('idadeCrianca').value} ano(s)`],
    ['Creche',       document.getElementById('crechePreferencia').value],
    ['Turno',        turnoMap[document.getElementById('turno').value] || '—'],
    ['Critérios',    criterios.length ? criterios.join(', ') : 'Nenhum'],
  ];

  document.getElementById('resumo').innerHTML =
    '<table style="width:100%;font-size:14px;border-collapse:collapse">' +
    rows.map(([label, val]) => `
      <tr>
        <td style="padding:8px 0;color:var(--gray-400);font-weight:700;width:140px;vertical-align:top">${label}</td>
        <td style="padding:8px 0;color:var(--gray-800)">${val || '—'}</td>
      </tr>`).join('') +
    '</table>';
}

async function enviarSolicitacao() {
  const btn = document.getElementById('btn-enviar');
  btn.innerHTML = '<span class="spinner"></span> Enviando...';
  btn.disabled = true;

  const payload = {
    nomeResponsavel:    document.getElementById('nomeResponsavel').value,
    cpfResponsavel:     document.getElementById('cpfResponsavel').value,
    telefoneResponsavel:document.getElementById('telefoneResponsavel').value,
    emailResponsavel:   document.getElementById('emailResponsavel').value,
    enderecoResponsavel:document.getElementById('enderecoResponsavel').value,
    nomeCrianca:        document.getElementById('nomeCrianca').value,
    idadeCrianca:       parseInt(document.getElementById('idadeCrianca').value),
    cpfCrianca:         document.getElementById('cpfCrianca').value,
    crechePreferencia:  document.getElementById('crechePreferencia').value,
    turno:              document.getElementById('turno').value,
    vulnerabilidadeSocial: document.getElementById('vulnerabilidadeSocial').checked,
    maeTrabalhadora:       document.getElementById('maeTrabalhadora').checked,
    rendaPerCapitaBaixa:   document.getElementById('rendaPerCapitaBaixa').checked,
    familiaMonoparental:   document.getElementById('familiaMonoparental').checked,
  };

  try {
    const res  = await fetch('/api/solicitacoes', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(payload),
    });
    const data = await res.json();

    if (res.ok) {
      document.getElementById('etapa-4').hidden = true;
      document.getElementById('sucesso').hidden  = false;
      document.getElementById('protocolo-box').textContent = data.protocolo;
      window.scrollTo({ top: 0, behavior: 'smooth' });
    } else {
      showAlert('error', 'Erro ao enviar: ' + (data.erro || 'Tente novamente.'));
      btn.innerHTML = 'Confirmar e enviar';
      btn.disabled = false;
    }
  } catch (e) {
    showAlert('error', 'Erro de conexão com o servidor.');
    btn.innerHTML = 'Confirmar e enviar';
    btn.disabled = false;
  }
}

function showAlert(type, msg) {
  const box = document.getElementById('alert-box');
  box.className = `alert alert-${type === 'error' ? 'error' : 'success'} show`;
  box.textContent = msg;
  box.scrollIntoView({ behavior: 'smooth', block: 'nearest' });
}
