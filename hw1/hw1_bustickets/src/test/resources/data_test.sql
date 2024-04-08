INSERT INTO stop (name, district)
VALUES
    ('Lisboa', 'Lisboa'),
    ('Coimbra', 'Coimbra'),
    ('Aveiro', 'Aveiro'),
    ('Porto', 'Porto'),
    ('Braga', 'Braga');

INSERT INTO leg (origin_stop_id, destination_stop_id, leg_time)
VALUES
    (1, 2, '02:02:00'),
    (1, 3, '02:30:00'),
    (2, 3, '00:48:00'),
    (2, 4, '01:15:00'),
    (3, 4, '00:48:00'),
    (3, 5, '01:19:00');

INSERT INTO route (id)
VALUES
    (1),
    (2),
    (3),
    (4),
    (5);

INSERT INTO route_legs (route_id, leg_id, legs_index)
VALUES
    (1, 1, 0),
    (1, 3, 1),
    (1, 5, 2),
    (2, 2, 0),
    (2, 6, 1),
    (3, 2, 0),
    (4, 4, 0),
    (5, 5, 0);

INSERT INTO trip (route_id, number_of_seats, price_euros, departure_time)
VALUES
    (1, 50, 25, '2024-06-02T10:15:00'),
    (2, 47, 50, '2024-06-02T11:15:00'),
    (3, 50, 20, '2024-06-03T10:15:00'),
    (4, 30, 22.5, '2024-06-02T11:30:00'),
    (5, 50, 20, '2024-06-04T06:15:00'),
    (5, 50, 20, CONCAT(CURRENT_DATE(), 'T07:00:00'));

INSERT INTO reservation (id, trip_id, seat_number, client_name, client_address)
VALUES
    (UNHEX('aa0f7252309c11eaa72a0242ac130002'), 1, 1, 'Miguel', 'Rua XPTO')
