let number;

let generateNumber = function () {
  return Math.floor(Math.random() * 10 + 1);
};

let addMessage = function (message, color) {
  let messagesList = document.querySelector("#messages");
  let li = document.createElement("li");
  let content = document.createTextNode(message);
  li.appendChild(content);
  messagesList.appendChild(li);
  li.style.backgroundColor = color;
};

let verifyNumber = function (e) {
  e.preventDefault();
  let input = e.target.querySelector('input[name="number"]');
  let n = Number(input.value);
  input.value = "";
  if (n == number) {
    //Crea il messaggio
    addMessage("Bravo, hai indovinato!", "green");
    //Mostra il bottone di reset
    document.querySelector("#reset").style.display = "inline";
    //Disabilita il form
    let input = document.querySelector('input[name="number"]');
    input.readOnly = true;
    input.disabled = true;
    //Disabilita il bottone di submit
    document.querySelector('input[type="submit"]').disabled = true;
  } else {
    if (n > number) {
      addMessage(`Hai scritto ${n}: troppo grande!`, "red");
    } else {
      addMessage(`Hai scritto ${n}: troppo piccolo!`, "yellow");
    }
  }
};

let resetNumber = function (e) {
  e.preventDefault();
  number = generateNumber();
  //Nasconde il bottone di reset
  document.querySelector("#reset").style.display = "none";
  document.querySelector("#messages").innerHTML = "";
  //Riabilita il form
  let input = document.querySelector('input[name="number"]');
  input.readOnly = false;
  input.disabled = false;
  input.focus();
  //Riabilita il bottono di submit
  document.querySelector('input[type="submit"]').disabled = false;
};

window.addEventListener("load", function () {
  number = generateNumber();
  document.querySelector("form").addEventListener("submit", verifyNumber);
  document.querySelector("#reset").addEventListener("click", resetNumber);
});
