# **Java-filmorate**

## *`Sprint 4`*
#### *- Refactor ER*
#### *- Refactor User model and Film model*

___

### *`Sprint 3`*
##### *- Add ER*
##### ![Entity relationship](/ER/ER.png)
##### *- Requests examples:*     
- *Get films:*       
  `SELECT * `  
`FROM films`
- *Get films by id:*     
  `SELECT * `  
  `FROM films`  
  `WHERE film_id=id`
- *Get ids of users liked the film:*     
  `SELECT user_liked_id `  
  `FROM film_likes`  
  ` WHERE film_id=id`
- *Get users:*     
  `SELECT * `  
  `FROM users`
- *Get users by id:*     
  `SELECT * `  
  `FROM users`    
  `WHERE user_id=id`
- *Get user friends ids:*     
  `SELECT friend_id `  
  `FROM users`  
  `WHERE user_id=id`
- *Get two users friendship:*   
  `SELECT uf.user_id, uf.friend_id,`   
  `FROM user_friends AS uf`  
  `WHERE (uf.user_id=u_id AND uf.friend_id=f_id)`   
  `OR (uf.user_id=f_id AND uf.friend_id=u_id)`

___

### *`Sprint 2`*
##### *- Add interfaces FilmStorage & UserStorage*
##### *- Add classes InMemoryFilmStorage & InMemoryUserStorage*
##### *- Refactor classes FilmController & UserController*
##### *- Add classes FilmService & UserService*
##### *- Refactor exception handlers*
##### *- Add integration tests*

---

### *`Sprint 1`*
##### *- Add Spring Boot (Web)*
##### *- Add model classes User and Film*
##### *- Add controllers*
##### *- Add constrains*
##### *- Add data validation*
##### *- Add REST API Tests*
##### *- Add logging*
##### *- Add ExceptionHandlers*
