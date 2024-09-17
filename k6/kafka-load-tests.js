import { check } from 'k6';
import http from 'k6/http';

export const options = {
    scenarios: {
        my_scenario1: {
            executor: 'constant-arrival-rate',
            duration: '120s', // total duration
            preAllocatedVUs: 250, // to allocate runtime resources

            rate: 10, // number of constant iterations given `timeUnit`
            timeUnit: '1s',
        },
    },
};

export default function () {
    const payload = JSON.stringify({
        "Teste":"SEU NOME"
     });
    const headers = { 'Content-Type': 'text/plain','Accept':'*/*' };
    const res = http.post('SUA URL', payload, { headers });

    check(res, {
        'Post status is 200': (r) => res.status === 200
    });
}
