
const form = document.getElementById('note-form');
const titleInput = document.getElementById('note-title');
const contentInput = document.getElementById('note-content');
const notesList = document.getElementById('notes-list');
const favoriteNotes = document.getElementById('favorite-notes');

let notes = [];

function renderNotes() {
    favoriteNotes.innerHTML = '';
    notesList.innerHTML = '';
    const favs = notes.filter(n => n.favorite);
    const others = notes.filter(n => !n.favorite);
    for (const note of favs) {
        favoriteNotes.appendChild(createNoteCard(note));
    }
    for (const note of others) {
        notesList.appendChild(createNoteCard(note));
    }
}

function createNoteCard(note) {
    const card = document.createElement('div');
    card.className = 'note-card' + (note.favorite ? ' favorite' : '');
    card.innerHTML = `
        <div class="note-header">
            <span class="note-title">${note.title}</span>
            <div class="note-actions">
                <span class="icon star" title="Favorito">${note.favorite ? '★' : '☆'}</span>
                <span class="icon delete" title="Eliminar">🗑️</span>
            </div>
        </div>
        <div class="note-content">${note.content}</div>
    `;
    card.querySelector('.star').onclick = () => {
        note.favorite = !note.favorite;
        renderNotes();
    };
    card.querySelector('.delete').onclick = () => {
        notes = notes.filter(n => n !== note);
        renderNotes();
    };
    return card;
}

form.onsubmit = e => {
    e.preventDefault();
    const title = titleInput.value.trim();
    const content = contentInput.value.trim();
    if (!title || !content) return;
    notes.push({ title, content, favorite: false });
    form.reset();
    renderNotes();
};


window.addEventListener('DOMContentLoaded', () => {
    const saved = localStorage.getItem('personalNotes');
    if (saved) notes = JSON.parse(saved);
    renderNotes();
});
window.addEventListener('beforeunload', () => {
    localStorage.setItem('personalNotes', JSON.stringify(notes));
});
