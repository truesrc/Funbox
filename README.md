Запустить Redis `redis-server`


POST http://localhost:8080/visited_links
Content-Type: application/json;charset=UTF-8

{   "links": [   "https://ya.ru",  "https://ya2.ru","https://ya3.ru","https://ya4.ru","https://ya5.ru", "https://ya.ru?q=123",   "funbox.ru",   "https://stackoverflow.com/questions/11828270/how-to-exit-the-vim-editor"   ] }

GET http://localhost:8080/visited_domains?from=1555410000&to=15554185760