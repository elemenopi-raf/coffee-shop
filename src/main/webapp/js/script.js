document.addEventListener('DOMContentLoaded', function () {

  /* ========== Hamburger Menu ========== */
  const hamburger = document.getElementById('hamburger');
  const navLinks = document.getElementById('nav-links');

  if (hamburger && navLinks) {
    hamburger.addEventListener('click', function () {
      this.classList.toggle('active');
      navLinks.classList.toggle('active');
    });

    navLinks.querySelectorAll('a').forEach(function (link) {
      link.addEventListener('click', function () {
        hamburger.classList.remove('active');
        navLinks.classList.remove('active');
      });
    });
  }

  /* ========== Sticky Nav ========== */
  const navbar = document.getElementById('navbar');
  if (navbar) {
    window.addEventListener('scroll', function () {
      navbar.classList.toggle('scrolled', window.scrollY > 100);
    });
  }

  /* ========== Scroll Animations ========== */
  if ('IntersectionObserver' in window) {
    const observer = new IntersectionObserver(function (entries) {
      entries.forEach(function (entry) {
        if (entry.isIntersecting) {
          entry.target.classList.add('visible');
        }
      });
    }, { threshold: 0.1 });

    document.querySelectorAll('.fade-in').forEach(function (el) {
      observer.observe(el);
    });

    document.querySelectorAll('.menu-item').forEach(function (el, i) {
      el.style.transitionDelay = (i * 0.08) + 's';
      observer.observe(el);
    });
  } else {
    document.querySelectorAll('.fade-in, .menu-item').forEach(function (el) {
      el.classList.add('visible');
    });
  }

  /* ========== Modal ========== */
  var modalOverlay = document.getElementById('modal-overlay');
  var modalMessage = document.getElementById('modal-message');
  var modalClose = document.getElementById('modal-close');

  var showModal = function (type, message) {
    if (!modalOverlay || !modalMessage) return;
    modalMessage.textContent = message;
    modalMessage.style.color = type === 'success' ? '#27ae60' : '#e74c3c';
    modalOverlay.classList.remove('hidden');
  };

  if (modalClose) {
    modalClose.addEventListener('click', function () {
      modalOverlay.classList.add('hidden');
    });
  }

  if (modalOverlay) {
    modalOverlay.addEventListener('click', function (e) {
      if (e.target === modalOverlay) {
        modalOverlay.classList.add('hidden');
      }
    });
  }

  /* ========== Contact Form (AJAX) ========== */
  var contactForm = document.querySelector('.contact-form');
  if (contactForm) {
    contactForm.addEventListener('submit', function (e) {
      e.preventDefault();

      var name = document.getElementById('name').value.trim();
      var email = document.getElementById('email').value.trim();
      var message = document.getElementById('message').value.trim();

      if (!name || !email || !message) {
        showModal('error', 'Please fill in all fields.');
        return;
      }

      if (!email.includes('@')) {
        showModal('error', 'Please enter a valid email.');
        return;
      }

      var btn = contactForm.querySelector('.btn');
      btn.classList.add('btn-loading');
      btn.disabled = true;

      fetch(contactForm.action, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/x-www-form-urlencoded',
          'X-Requested-With': 'XMLHttpRequest'
        },
        body: 'name=' + encodeURIComponent(name) +
              '&email=' + encodeURIComponent(email) +
              '&message=' + encodeURIComponent(message)
      })
      .then(function (r) { return r.json(); })
      .then(function (data) {
        showModal(data.type, data.message);
        if (data.type === 'success') {
          contactForm.reset();
        }
      })
      .catch(function () {
        showModal('error', 'Something went wrong. Please try again.');
      })
      .finally(function () {
        btn.classList.remove('btn-loading');
        btn.disabled = false;
      });
    });
  }

  /* ========== Newsletter Form ========== */
  var newsletterForm = document.querySelector('.newsletter-form');
  if (newsletterForm) {
    newsletterForm.addEventListener('submit', function (e) {
      var input = this.querySelector('input');
      var msgEl = document.getElementById('newsletter-message');
      if (!msgEl) {
        msgEl = document.createElement('p');
        msgEl.id = 'newsletter-message';
        this.parentNode.appendChild(msgEl);
      }

      if (!input.value.trim() || !input.value.includes('@')) {
        e.preventDefault();
        msgEl.textContent = 'Please enter a valid email.';
        msgEl.style.color = '#e74c3c';
        return;
      }
    });
  }

});
