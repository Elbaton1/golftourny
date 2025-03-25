# Golf Club Tournament API

A Spring Boot REST API that is for golf club's members and tournaments. you can create, get, and add members and tournaments, with Docker and MySQL.

---

## ✨ Features

### Members
- Add new members
- Retrieve all members
- Search members by:
  - Name
  - Membership type
  - Phone number
  - Tournament start date

### Tournaments
- Add new tournaments
- get all tournaments
- Search tournaments by:
  - date
  - location
- Add members to tournaments
- View all members in a tournament

---

## 🖥️ Endpoints

### Member Endpoints
- `POST /members` - Add a member
- `GET /members` - Get all members
- `GET /members/search?name=John` - Search name
- `GET /members/search?type=Gold` - Search membership type
- `GET /members/search?phone=1234567890` - Search phone number
- `GET /members/search?tournamentStartDate=2024-05-01` - Members in tournaments starting that date

### Tournament Endpoints
- `POST /tournaments` - Add a tournament
- `GET /tournaments` - Get all tournaments
- `GET /tournaments/search?startDate=2024-06-01` - Search date
- `GET /tournaments/search?location=Halifax` - Search location
- `POST /tournaments/{id}/addMember` - Add member to a tournament

---

## 🚀 Run the Project with Docker

1. **Clone the repo**:
```bash
git clone https://github.com/Elbaton1/golftourny.git
cd golftourny
```

2. **Start services with Docker Compose**:
```bash
docker-compose up --build
```

The application will run at `http://localhost:8080` and connect to MySQL as in `docker-compose.yml`.

---

## 👀 Screenshots
Postman test results are included in the `/screenshots/` folder

## 📅 Deliverables Summary
- [x] GitHub link: [https://github.com/Elbaton1/golftourny](https://github.com/Elbaton1/golftourny)
- [x] API Testing Screenshots: in `screenshots/`
- [x] Docker Support with MySQL
- [x] README with instructions and search API details

---

built with luv ❤️

By: Michael O'brien


