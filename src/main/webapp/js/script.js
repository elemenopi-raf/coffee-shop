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

  /* ========== Contact Form ========== */
  var contactForm = document.querySelector('.contact-form');
  if (contactForm) {
    contactForm.addEventListener('submit', function (e) {
      var name = document.getElementById('name').value.trim();
      var email = document.getElementById('email').value.trim();
      var message = document.getElementById('message').value.trim();

      var msgEl = document.getElementById('form-message');
      if (!msgEl) {
        msgEl = document.createElement('p');
        msgEl.id = 'form-message';
        contactForm.appendChild(msgEl);
      }

      if (!name || !email || !message) {
        e.preventDefault();
        msgEl.textContent = 'Please fill in all fields.';
        msgEl.style.color = '#e74c3c';
        return;
      }

      if (!email.includes('@')) {
        e.preventDefault();
        msgEl.textContent = 'Please enter a valid email.';
        msgEl.style.color = '#e74c3c';
        return;
      }

      var btn = contactForm.querySelector('.btn');
      btn.classList.add('btn-loading');
      btn.disabled = true;
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
