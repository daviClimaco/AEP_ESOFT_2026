async function carregarDashboard() {
  const btn = document.getElementById('btn-refresh');
  btn.innerHTML = '<span class="spinner" style="border-top-color:var(--blue-500);border-color:var(--gray-200)"></span>';
  btn.disabled = true;

  try {
    const res  = await fetch('/api/gestor/dashboard');
    const d    = await res.json();

    document.getElementById('m-total').textContent    = d.totalSolicitacoes;
    document.getElementById('m-aprovadas').textContent = d.vagasPreenchidas;
    document.getElementById('m-fila').textContent     = d.naFilaDeEspera;
    document.getElementById('m-alta').textContent     = d.altaPrioridade;

    const creche = d.solicitacoesPorCreche || {};
    const total  = Object.values(creche).reduce((a, b) => a + b, 0) || 1;

    document.getElementById('barras-creche').innerHTML =
      Object.entries(creche).length
        ? Object.entries(creche).map(([nome, qtd]) => {
            const pct = Math.round((qtd / total) * 100);
            return `
              <div class="bar-row">
                <span class="bar-label">${nome}</span>
                <div class="bar-track">
                  <div class="bar-fill" style="width:${pct}%">${pct}%</div>
                </div>
              </div>`;
          }).join('')
        : '<p style="font-size:13px;color:var(--gray-400)">Nenhuma solicitação cadastrada.</p>';

    const prio   = d.distribuicaoPorPrioridade || {};
    const CORES  = {
      ALTA:   ['var(--red-bg)',    'var(--red-text)'],
      MEDIA:  ['var(--orange-bg)', 'var(--orange-text)'],
      NORMAL: ['var(--green-bg)',  'var(--green-text)'],
    };
    const LABELS = { ALTA: 'Alta prioridade', MEDIA: 'Média prioridade', NORMAL: 'Prioridade normal' };

    document.getElementById('dist-prioridade').innerHTML =
      ['ALTA', 'MEDIA', 'NORMAL'].map(k => {
        const [bg, color] = CORES[k];
        return `
          <div class="prio-row" style="background:${bg};color:${color}">
            <span>${LABELS[k]}</span>
            <span style="font-size:20px;font-family:'Nunito',sans-serif">${prio[k] || 0}</span>
          </div>`;
      }).join('');
  } catch (e) {
    document.getElementById('m-total').textContent = '—';
  } finally {
    btn.innerHTML = '<i class="ti ti-refresh"></i> Atualizar';
    btn.disabled  = false;
  }
}

carregarDashboard();
