insert into ChessGame_HibernateFluent (playerBlack, playerWhite, round, version, id) values ('Magnus Carlsen', 'Anish Giri', 1, 0, 100);
insert into ChessMove_HibernateFluent (color, game_id, move, moveNumber, id) values ('WHITE', 100, 'c4', 1, 100);
insert into ChessMove_HibernateFluent (color, game_id, move, moveNumber, id) values ('BLACK', 100, 'Nf6', 1, 101);

insert into ChessGame_HibernateSetter (playerBlack, playerWhite, round, version, id) values ('Magnus Carlsen', 'Anish Giri', 1, 0, 100);
insert into ChessMove_HibernateSetter (color, game_id, move, moveNumber, id) values ('WHITE', 100, 'c4', 1, 100);
insert into ChessMove_HibernateSetter (color, game_id, move, moveNumber, id) values ('BLACK', 100, 'Nf6', 1, 101);