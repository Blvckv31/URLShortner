# URL Shortener

A scalable URL shortening service designed for low-latency redirects, high write throughput, and predictable ID generation.

---

## Overview

This project implements a URL shortening system that converts long URLs into compact, unique identifiers and supports fast redirection.

The design prioritizes:
- Constant-time lookups  
- Collision-safe ID generation  
- Horizontal scalability  

---

## Screenshots

### Login Screen
![Login Screen](https://github.com/Gitster7/URLShortner/blob/development/home.png?raw=true)

### Application Interface
![App Screen](https://github.com/Gitster7/URLShortner/blob/development/app.png?raw=true)

---

## Core Features

- URL shortening with unique ID generation  
- Authenticated access (login/logout)  
- Fast redirection via short links  
- Copy-to-clipboard UX  
- Extensible architecture for analytics and rate limiting  

---

## Tech Stack

**Frontend**
- React / Bootstrap 

**Backend**
- Spring Boot

**Database**
- Postgres

**Cache**
- Redis  

---

## System Architecture

    Client
      ↓
    API Gateway / Controller
      ↓
    Service Layer
      ├── URL Encoding / ID Generation
      ├── Cache Layer (Redis)
      └── Persistence Layer (DB)

---

## Data Model

**URL Table**

| Field       | Description        |
|------------|--------------------|
| id         | Primary key        |
| short_id   | Unique identifier  |
| long_url   | Original URL       |
| created_at | Timestamp          |
| updated_at | Timestamp          |

---

## ID Generation Strategy

### Approach: Base62 Encoding of Numeric IDs

- Generate a unique numeric ID (auto-increment or sequence)
- Convert to Base62 (`[a-zA-Z0-9]`) to produce short strings

Example:
    125 → "cb"

### Why Base62?

- Compact representation  
- URL-safe characters  
- Deterministic and fast  

---

## Alternative Strategies (Tradeoffs)

| Strategy                  | Pros                      | Cons                           |
|--------------------------|---------------------------|--------------------------------|
| Auto-increment + Base62  | Simple, fast              | Harder to scale across shards  |
| Hash (MD5/SHA)           | No central counter        | Collision handling required    |
| Random strings           | Decentralized             | Collision probability          |
| Snowflake IDs            | Distributed, scalable     | More complex setup             |

---

## Read Path (Redirection Flow)

1. User hits `/abc123`  
2. Check Redis cache  
   - Hit → return long URL  
   - Miss → query database  
3. Store result in Redis  
4. Redirect user  

---

## Write Path (Shortening Flow)

1. Validate input URL  
2. Generate unique ID  
3. Encode to Base62  
4. Store mapping in DB  
5. Cache result (optional)  
6. Return short URL  

---

## Caching Strategy (Redis)

- Key: `short_id → long_url`  
- TTL: Configurable  

### Benefits

- Reduces DB reads  
- Improves latency  
- Handles traffic spikes  

---

## Scaling Considerations

### Database
- Read replicas for heavy traffic  
- Sharding by ID or hash  

### Cache
- Redis cluster  
- Cache hot links  

### ID Generation
- Move to distributed IDs (Snowflake)  
- Avoid single bottleneck  

### Load Balancing
- Stateless services  
- Horizontal scaling  

---

## Performance

- O(1) redirect lookup  
- Cache-first design  
- Minimal response overhead  

---

## Reliability

- Handle collisions (if applicable)  
- Validate URLs  
- Graceful cache fallback  

---

## Security

- Rate limiting  
- Input sanitization  
- Auth for management APIs  

---


## Future Improvements

- Custom aliases   
- Expiration  
- QR codes  

---

## Key Takeaway

This project focuses on building a scalable foundation for URL shortening with clear paths toward distributed systems design.
