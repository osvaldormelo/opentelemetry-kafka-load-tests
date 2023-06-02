import { check } from 'k6';
import http from 'k6/http';

export const options = {
    scenarios: {
        my_scenario1: {
            executor: 'constant-arrival-rate',
            duration: '60s', // total duration
            preAllocatedVUs: 50, // to allocate runtime resources

            rate: 50, // number of constant iterations given `timeUnit`
            timeUnit: '1s',
        },
    },
};

export default function () {
    const payload = JSON.stringify({
        name: 'lorem',
        surname: 'ipsum',
    });
    const headers = { 'Content-Type': 'text/plain','Accept':'*/*' };
    const res = http.post('http://camel-quarkus-kafka-api-producer-camel-quarkus-apps.apps.cluster-bwrbh.bwrbh.sandbox830.opentlc.com/produce', payload, { headers });

    check(res, {
        'Post status is 200': (r) => res.status === 200
    });
}