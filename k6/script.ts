import http from 'k6/http';
import { check, sleep } from 'k6';

export const options = {
    vus: 100,
    duration: '30s',
};

const users = [
    { email: 'admin@example.com', password: 'admin123' },
    { email: 'teacher@example.com', password: 'teacher123' },
    { email: 'student@example.com', password: 'student123' },
];

function getRandomUser() {
    return users[Math.floor(Math.random() * users.length)];
}

export default function () {
    const user = getRandomUser();

    const payload = JSON.stringify({
        email: user.email,
        password: user.password,
    });

    const params = {
        headers: {
            'Content-Type': 'application/json',
        },
    };

    const res = http.post('http://localhost:8080/api/users/login', payload, params);

    check(res, {
        'login successful': (r) => r.status === 200,
    });

    sleep(1);
}

