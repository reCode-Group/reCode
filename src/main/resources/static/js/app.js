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
});
