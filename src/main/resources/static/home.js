function beginGame() {
  $.ajax({
    url: "http://localhost:8080/api/begin",
    method: "POST",
    success: function (id) {
      loadGame(id);
      console.log(id);
    },
  });
}

function loadGame(id) {
  $.ajax({
    url: "http://localhost:8080/api/games/" + id,
    method: "GET",
    success: function (response) {
      console.log(response);
      fillBoard(response);
    },
  });
}

function loadAllGames() {
  $.ajax({
    url: "http://localhost:8080/api/games",
    method: "GET",
    success: function (response) {
      console.log(response);
    },
  });
}

function fillBoard(response) {
  $("#insertGameId").text(response.gameId);
  if (response.pastMoves != null) {
    for (i = 0; i < response.pastMoves.length; i++) {
      $(`#${response.pastMoves[i].choice}`).text(response.pastMoves[i].player);
      console.log(response.pastMoves[i]);
    }
  } else {
    $("#A1").text("-");
    $("#A2").text("-");
    $("#A3").text("-");
    $("#B1").text("-");
    $("#B2").text("-");
    $("#B3").text("-");
    $("#C1").text("-");
    $("#C2").text("-");
    $("#C3").text("-");
  }
}
