document.addEventListener("DOMContentLoaded", function () {
    // Находим элемент .arrow-container
    var arrowContainer = document.querySelector('.arrow-container');

    // Находим элемент кнопки
    var showButton = document.querySelector('.show-button');
    var burgerMenu = document.querySelector('.burger-menu');
    var overlay = document.querySelector('.menu-overlay');

    // Находим все элементы nav-link
    var navLinks = document.querySelectorAll('.burger-menu .nav-link');

    // Устанавливаем значение, при котором кнопка должна появиться
    var scrollThreshold = 300;

    // Функция для проверки положения прокрутки и применения стилей
    function checkScroll() {
        // Проверяем, был ли успешно найден arrowContainer
        if (arrowContainer) {
            if (burgerMenu && burgerMenu.classList.contains('active')) {
                arrowContainer.style.display = 'none';
                return;
            }
            if (window.scrollY >= scrollThreshold) {
                // Если прокрутка превысила пороговое значение, добавляем стиль
                arrowContainer.style.display = 'flex';
            } else {
                // В противном случае скрываем кнопку
                arrowContainer.style.display = 'none';
            }
        }
    }

    // Функция для прокрутки страницы к верху при клике на кнопку
    function toggleMenu() {
        if (showButton && burgerMenu && overlay) {
            if (burgerMenu.classList.contains('active')) {
                showButton.querySelector('use').setAttribute('xlink:href', '#show');
                burgerMenu.classList.remove('active');
                overlay.style.display = 'none';
                overlay.style.visibility = 'hidden';
            } else {
                showButton.querySelector('use').setAttribute('xlink:href', '#hide');
                burgerMenu.classList.add('active');
                overlay.style.display = 'block';
                overlay.style.visibility = 'visible';
            }
        }
    }

    // Функция скрытия burgerMenu при изменении размера окна
    function handleResize() {
        if (showButton && burgerMenu && overlay) {
            showButton.querySelector('use').setAttribute('xlink:href', '#show');
            burgerMenu.classList.remove('active');
            overlay.style.display = 'none';
            overlay.style.visibility = 'hidden';
        }
    }

    // Вызываем функцию при загрузке страницы и при каждой прокрутке
    window.addEventListener('scroll', checkScroll);
    checkScroll(); // Проверяем положение прокрутки при загрузке страницы

    // Добавляем обработчик клика на кнопку
    if (showButton) {
        showButton.addEventListener('click', toggleMenu);
    }

    // Добавляем обработчик изменения размера окна
    window.addEventListener('resize', handleResize);

    // Добавляем обработчик клика на каждый nav-link
    navLinks.forEach(function (navLink) {
        navLink.addEventListener('click', toggleMenu);
    });


// Модальные окна
    function openModal(selector) {
        const modals = document.querySelector('.modals');
        const modal = document.querySelector(selector);

        if (modals && modal) {
            modals.style.display = 'flex';
            modal.style.display = 'block';
            document.body.classList.add('modals-scroll');
        }
    }

    function closeModal() {
        const modals = document.querySelector('.modals');
        const allModals = document.querySelectorAll('.modal');

        if (modals) {
            modals.style.display = 'none';
        }

        allModals.forEach((modal) => {
            modal.style.display = 'none';
        });

        document.body.classList.remove('modals-scroll');
    }

// Открытие модального окна при клике на элемент с data-modal-target
    document.querySelectorAll('[data-modal-target]').forEach((trigger) => {
        trigger.addEventListener('click', function (evt) {
            evt.preventDefault();
            const target = this.getAttribute('data-modal-target');
            openModal(target);
        });
    });

// Закрытие модального окна при клике на элемент с классом js-modal-close
    document.addEventListener('click', function (evt) {
        if (evt.target.classList.contains('js-modal-close')) {
            closeModal();
        }
    });

// Закрытие модального окна по нажатию на Esc
    document.addEventListener('keydown', function (evt) {
        if (evt.key === 'Escape') {
            closeModal();
        }
    });

// Создание глобального API для управления модальными окнами
    window.api = {
        modals: {
            open: openModal,
            close: closeModal,
        },
    };
});


