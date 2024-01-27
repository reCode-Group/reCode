document.addEventListener("DOMContentLoaded", function () {
    // Находим элемент .arrow-container
    var arrowContainer = document.querySelector('.arrow-container');

    // Устанавливаем значение, при котором кнопка должна появиться
    var scrollThreshold = 300;

    // Функция для проверки положения прокрутки и применения стилей
    function checkScroll() {
        if (window.scrollY >= scrollThreshold) {
            // Если прокрутка превысила пороговое значение, добавляем стиль
            arrowContainer.style.display = 'flex';
        } else {
            // В противном случае скрываем кнопку
            arrowContainer.style.display = 'none';
        }
    }

    // Вызываем функцию при загрузке страницы и при каждой прокрутке
    window.addEventListener('scroll', checkScroll);
    checkScroll(); // Проверяем положение прокрутки при загрузке страницы
});
