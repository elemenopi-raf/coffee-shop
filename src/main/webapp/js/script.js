document.addEventListener('DOMContentLoaded', function () {
  const form = document.getElementById('contact-form');
  if (!form) return;

  form.addEventListener('submit', function (e) {
    const name = document.getElementById('name').value.trim();
    const email = document.getElementById('email').value.trim();
    const message = document.getElementById('message').value.trim();

    if (!name || !email || !message) {
      e.preventDefault();
      const msgEl = document.getElementById('form-message');
      msgEl.textContent = 'Please fill in all fields.';
      msgEl.style.color = '#e74c3c';
      return;
    }

    if (!email.includes('@')) {
      e.preventDefault();
      const msgEl = document.getElementById('form-message');
      msgEl.textContent = 'Please enter a valid email.';
      msgEl.style.color = '#e74c3c';
      return;
    }
  });
});
