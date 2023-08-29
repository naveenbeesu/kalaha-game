$(document).ready(function () {
    const INACTIVE = "inactive";
    const ACTIVE = "active";
    const PLAYER1 = "player1";
    const PLAYER2 = "player2";

    startGame(false);

    $("#begin-game").click(function () {
        startGame(true);
    });

    function startGame(isNewGame = false) {
        $("#winner").hide();
        let id = null;
        if (!isNewGame) {
            id = localStorage.getItem('id');
        }
        $.ajax({
            url: "/kalaha/startGame",
            data: {
                id: id
            },
            success: function (result) {
                createBoard(result);
            },
            error: function (result) {
                alert("Status: " + JSON.stringify(result));
            }
        });
    }

    $(".pit").click(function () {
        if ($(this).hasClass(INACTIVE)) return;

        let id = localStorage.getItem('id');
        $.ajax({
            url: "/kalaha/moveStones",
            type: "POST",
            data:
                JSON.stringify({
                    id: id,
                    pitIndex: $(this).index() - 1,
                    player: $(this).parent().attr('id')
                }),
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            success: function (result) {
                createBoard(result);
            },
            error: function (result) {
                alert("Status: " + JSON.stringify(result));
            }
        });
    });

    function createBoard(result) {
        localStorage.setItem('id', result.id);
        $.each(result.player1.stonesInPits, function (index, stones) {
            $("#player1 .pit").eq(index).html(stones)
        });

        $.each(result.player2.stonesInPits, function (index, stones) {
            $("#player2 .pit").eq(index).html(stones)
        });
        $("#player1 #BigPitOfPlayer1 > span").html(result.player1.stonesInBigPit);
        $("#player2 #BigPitOfPlayer2 > span").html(result.player2.stonesInBigPit);


        switch (result.presentPlayer) {
            case PLAYER1:
                $("#player1 .pit").removeClass(INACTIVE).addClass(ACTIVE);
                $("#player2 .pit").removeClass(ACTIVE).addClass(INACTIVE);
                $("#currentMove > span").html(PLAYER1);
                break;
            case PLAYER2:
                $("#player1 .pit").removeClass(ACTIVE).addClass(INACTIVE);
                $("#player2 .pit").removeClass(INACTIVE).addClass(ACTIVE);
                $("#currentMove > span").html(PLAYER2);
                break;
        }
        if (result.winnerOfGame!=="No Winner") {
            $("#player1 div, #player2 div").removeClass(ACTIVE).addClass(INACTIVE);
            $('#winner').show().children("span").html(result.winnerOfGame);
        }
    }
});
