const URL = 'https://thesimpsonsapi.com/api';
const grid = document.getElementById('character-grid');
const modal = document.getElementById('modal');
const modalBody = document.getElementById('modal-body');

let currentPage = 1;
let totalPages = 1;

// Diccionarios de traducción
const translations = {
  gender: {
    'Male': 'Masculino',
    'Female': 'Femenino',
    'Unknown': 'Desconocido'
  },
  status: {
    'Alive': 'Vivo',
    'Deceased': 'Fallecido',
    'Unknown': 'Desconocido'
  },
  occupation: {
    'Assistant to Mr. Burns': 'Asistente del Sr. Burns',
    "Bartender and Owner of Moe's Tavern": 'Cantinero y Dueño de la Taberna de Moe',
    'Employee of Department of Motor Vehicles': 'Empleada del Departamento de Vehículos Motorizados',
    'Fourth grade teacher at Springfield Elementary School (formerly)': 'Maestra de cuarto grado en la Escuela Primaria de Springfield (anteriormente)',
    'Housewife (former)': 'Ama de casa (anteriormente)',
    'Owner & Director of the Springfield Nuclear Power Plant': 'Dueño y Director de la Planta de Energía Nuclear de Springfield',
    'Owner of the Kwik-E-Mart': 'Dueño del Kwik-E-Mart',
    'Principal of Springfield Elementary School': 'Director de la Escuela Primaria de Springfield',
    'Retired': 'Jubilado',
    'Safety Inspector': 'Inspector de Seguridad',
    'Springfield DMV Employee': 'Empleada del DMV de Springfield',
    'Student at Springfield Elementary School': 'Estudiante en la Escuela Primaria de Springfield',
    'Student at Springfield Elementary School, CTU Agent, Hall-monitor, Member of PETA': 'Estudiante, Agente de la CTU, Monitor de pasillo, Miembro de PETA',
    'Student at Springfield Elementary SchoolStudent at a religious private school (formerly)': 'Estudiante en la Escuela Primaria de Springfield (anteriormente en escuela religiosa)',
    'Superintendent of Springfield Elementary School': 'Superintendente de la Escuela Primaria de Springfield',
    'Television personality': 'Personalidad de Televisión',
    'The Leftorium (formerly)': 'El Zurdorium (anteriormente)',
    'Unemployed': 'Desempleada',
    'Unknown': 'Desconocida',
    // Página 2 y otras
    'Chief of the Springfield Police Department': 'Jefe del Departamento de Policía de Springfield',
    'Criminal Mastermind': 'Mente Criminal Maestra',
    'Doctor at Springfield General Hospital': 'Doctor en el Hospital General de Springfield',
    'Helicopter pilot': 'Piloto de Helicóptero',
    'Housewife': 'Ama de casa',
    'Police Sergeant': 'Sargento de Policía',
    'Professor': 'Profesor',
    "Professor Frink's Inventions": 'Inventos del Profesor Frink',
    'Safety Operations Supervisor at the Springfield Nuclear Power Plant': 'Supervisor de Operaciones de Seguridad en la Planta Nuclear',
    'School bully': 'Bravucón de la escuela',
    'School Bully': 'Bravucón de la escuela',
    'School bus driver': 'Conductor del autobús escolar',
    'School student': 'Estudiante de escuela',
    'Student and Bully at Springfield Elementary': 'Estudiante y Bravucón en la Primaria de Springfield',
    'Student at Springfield Elementary': 'Estudiante en la Primaria de Springfield',
    'Student at Springfield Elementary School.': 'Estudiante en la Escuela Primaria de Springfield',
    'Technical Supervisor at the Springfield Nuclear Power Plant': 'Supervisor Técnico en la Planta Nuclear'
  }
};

// Función auxiliar para traducir
function translate(type, value) {
  if (!value) return 'Desconocido';
  return translations[type][value] || value;
}

async function renderCharacters(page) {
  grid.innerHTML = "<div class='loading'>Cargando personajes...</div>";
  
  try {
    const response = await fetch(`${URL}/characters?page=${page}`);
    if (!response.ok) throw new Error('Error en la red');
    const data = await response.json();

    totalPages = data.pages;
    grid.innerHTML = ""; // Limpiar cargando
    
    data.results.forEach(char => {
      const card = document.createElement('div');
      card.className = 'card';
      card.innerHTML = `
        <div class="img-container">
          <img src="https://cdn.thesimpsonsapi.com/500${char.portrait_path}" alt="${char.name}" loading="lazy">
        </div>
        <h3>${char.name}</h3>
        <p><strong>Ocupación:</strong> ${translate('occupation', char.occupation)}</p>
        <p><strong>Edad:</strong> ${char.age ? char.age + ' años' : 'Desconocida'}</p>
        <p><strong>Estado:</strong> ${translate('status', char.status)}</p>
        <button class="btn" onclick="showDetail(${char.id})">Ver Detalles</button>
      `;
      grid.appendChild(card);
    });

    updatePagination(page, data.pages);
  } catch (error) {
    console.error("Error al obtener personajes:", error);
    grid.innerHTML = "<div class='loading'>Error al cargar los datos. Por favor, intenta de nuevo.</div>";
  }
}

function updatePagination(current, total) {
  document.getElementById('page-info').innerText = `Página ${current} de ${total}`;
  document.getElementById('prev-btn').disabled = current === 1;
  document.getElementById('next-btn').disabled = current === total;
}

function changePage(offset) {
  const newPage = currentPage + offset;
  if (newPage >= 1 && newPage <= totalPages) {
    currentPage = newPage;
    renderCharacters(currentPage);
    window.scrollTo({ top: 0, behavior: 'smooth' });
  }
}

// Inicializar
renderCharacters(currentPage);

async function showDetail(id) {
  modal.style.display = "block";
  modalBody.innerHTML = "<div class='loading'>Cargando detalles...</div>";

  try {
    const response = await fetch(`${URL}/characters/${id}`);
    if (!response.ok) throw new Error('Error en la red');
    const char = await response.json();
    
    const hasPhrases = char.phrases && char.phrases.length > 0;
    const phrasesHtml = hasPhrases 
      ? `<ul class="phrases-list">${char.phrases.map(p => `<li>${p}</li>`).join('')}</ul>`
      : '<p><em>No tiene frases registradas.</em></p>';

    modalBody.innerHTML = `
      <div class="modal-header">
        <img src="https://cdn.thesimpsonsapi.com/500${char.portrait_path}" alt="${char.name}" class="modal-img">
        <div class="modal-info">
          <h2>${char.name}</h2>
          <p><strong>Edad:</strong> ${char.age ? char.age + ' años' : 'Desconocida'}</p>
          <p><strong>Género:</strong> ${translate('gender', char.gender)}</p>
          <p><strong>Estado:</strong> ${translate('status', char.status)}</p>
          <p><strong>Ocupación:</strong> ${translate('occupation', char.occupation)}</p>
        </div>
      </div>
      <div class="phrases-container">
        <h4>Frases Célebres</h4>
        ${phrasesHtml}
      </div>
    `;
  } catch (error) {
    console.error("Error al obtener detalles:", error);
    modalBody.innerHTML = "<div class='loading'>Error al cargar los detalles.</div>";
  }
}

function closeModal() {
  modal.style.display = "none";
}

// Cerrar modal al hacer clic fuera de él
window.onclick = function(event) {
  if (event.target == modal) {
    closeModal();
  }
}