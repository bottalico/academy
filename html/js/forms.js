window.addEventListener("load", () => {
  console.log("Il mio primo js dentro a una pagina!!!");

  document.querySelectorAll("h2")[1].addEventListener("click", (e) => {
    e.stopPropagation();
    console.log("Event listener di h2");
    console.log(e);
  });

  document.querySelector("body").addEventListener("click", (e) => {
    console.log("Event listener di body");
    console.log(e);
  });

  document.querySelectorAll("form")[1].addEventListener("submit", (e) => {
    e.stopPropagation();
    e.preventDefault();
    console.log("Event listener di form");
    console.log(e);
  });
});
