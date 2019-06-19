# codefellowship
This repo contains a spring web application that allows users to create accounts and login and it shows them 
thier individual profile pages


Allow users to log in to CodeFellowship and create posts.

Using the above cheat sheet, add the ability for users to log in to your app.
Upon logging in, users should be taken to a /myprofile route that displays their information.
Ensure that your homepage, login, and registration routes are accessible to non-logged in users. All other routes should be limited to logged-in users.
Ensure that user registration also logs users into your app automatically.
Add a Post entity to your app.
A Post has a body and a createdAt timestamp.
A logged-in user should be able to create a Post, and a post should belong to the user that created it.
hint: this is a relationship between two pieces of data
A user’s posts should be visible on their profile page.
When a user is logged in, the app should display the user’s username on every page (probably in the heading).


## Instructions to Run the Application:
type "/gradlew bootRun" on your command line
