# Dogs Management React.js Frontend

This is the React.js frontend for the Dogs Management application. It provides a user interface for managing dogs through the DogClient interface.

## Project Setup

### Prerequisites

- Node.js (v18.16.0 or later)
- npm (v9.5.1 or later)

### Installation

```bash
# Install dependencies
npm install
```

### Development

```bash
# Serve with hot reload at localhost:3000
npm start
```

### Production Build

```bash
# Build for production
npm run build
```

### Testing

```bash
# Run tests
npm test
```

## Project Structure

- `public/` - Static assets
- `src/` - Source code
  - `components/` - React components
    - `Home.js` - Home page component
    - `DogList.js` - List of dogs component
    - `DogForm.js` - Form for adding/editing dogs component
  - `App.js` - Root component
  - `index.js` - Entry point
  - `index.css` - Global styles with Spring Framework theme

## Integration with Spring Boot

This React.js application is integrated with a Spring Boot backend. The frontend is built and packaged with the Spring Boot application using the frontend-maven-plugin.

The React.js application communicates with the backend through REST APIs exposed by the DogController.

## Spring Framework Theme

The application uses a custom Spring Framework theme that includes Spring colors and styling. The theme is defined in `src/index.css`.

## Features

- View a list of dogs
- Add new dogs
- Edit existing dogs
- Delete dogs
- Responsive design for mobile and desktop
