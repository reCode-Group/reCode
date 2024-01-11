const scrollwrapper = document.querySelector('.carousel-container');
let isDown = false;
let startX;
let scrollLeft;

scrollwrapper.addEventListener('mousedown', (e) => {
  isDown = true;
  startX = e.pageX - scrollwrapper.offsetLeft;
  scrollLeft = scrollwrapper.scrollLeft;
});
scrollwrapper.addEventListener('mouseleave', () => {
  isDown = false;
});
scrollwrapper.addEventListener('mouseup', () => {
  isDown = false;
});
scrollwrapper.addEventListener('mousemove', (e) => {
  if(!isDown) return;
  e.preventDefault();
  const x = e.pageX - scrollwrapper.offsetLeft;
  const walk = (x - startX) * 1;
  scrollwrapper.scrollLeft = scrollLeft - walk;
});

const scrollToRight = () => {
	scrollwrapper.scrollBy({
		left: 200,
		behavior: 'smooth'
	});
}

const scrollToLeft = () => {
	scrollwrapper.scrollBy({
		left: -200,
		behavior: 'smooth'
	});
}
