# TV Series Database Manager 

A Java-based backend system designed to manage a database of streaming services, TV series, actors, and user reviews. This project focuses on **Object-Oriented Programming (OOP)**, data integrity, and custom business logic.

---

## Key Features

* **Content Management (R1):** Full management for streaming services (Netflix, Prime, etc.), TV series, and unique actor profiles.
* **Season & Episode Tracking (R2):** Chronological validation for release dates and a monitoring tool to identify incomplete seasons.
* **User Personalization (R3):** Profile management with favorite genres and an **intelligent suggestion engine**.
* **Rating & Feedback (R4):** Score management (0-10) with real-time average calculation per series and per user.
* **Advanced Analytics (R5):**
    * **Most Awaited:** Identifies upcoming seasons based on the highest-rated series.
    * **Best Actors:** Filters actors who have worked exclusively on high-rated series (> 8.0).

---

## Technical Stack

* **Language:** Java
* **Architecture:** Object-Oriented (OOP)
* **Error Handling:** Custom exception management via `TSException`.
* **Data Management:** Advanced use of `HashMap`, `TreeMap`, and `Streams` for data filtering.
