
START TRANSACTION;

--
-- Baza danych: `pzg2`
--

USE pzg2;


-- --------------------------------------------------------


--
-- Zrzut danych tabeli `reservations`
--

INSERT INTO `reservations` (`id`, `room_id`, `start`, `end`, `name`, `lecturer_mail`, `lecturer_first_name`, `lecturer_last_name`, `participants_count`, `reservation_datetime`, `accepted`) VALUES
(1, 3161, '2024-04-11 08:00:00', '2024-04-11 12:00:00', 'Warsztaty programowania zespolowego', 'jankowalski@example.com', 'jan', 'kowalski', 30, '2024-04-05 21:40:52', 1),
(2, 3162, '2024-04-22 12:00:00', '2024-04-22 14:00:00', 'Warsztaty algorytmiczne', 'jankowalski@example.com', 'jan', 'kowalski', 7, '2024-04-17 17:37:23', 0),
(3, 3161, '2024-04-23 16:00:00', '2024-04-23 18:00:00', 'Laboratoria Java', 'jankowalski@example.com', 'jan', 'kowalski', 21, '2024-04-17 17:56:58', 0),
(4, 3161, '2024-04-24 16:00:00', '2024-04-24 18:00:00', 'Jezyk C++', 'USOS', 'USOS', 'USOS', 21, '2024-04-17 17:56:58', 1);

-- --------------------------------------------------------

--
-- Zrzut danych tabeli `rooms`
--

INSERT INTO `rooms` (`id`, `name`, `capacity`) VALUES
(3172, 'A-0-11', 18),
(3170, 'A-0-13', 33),
(3171, 'A-0-15', 24),
(3164, 'A-1-03', 108),
(3165, 'A-1-04', 31),
(3161, 'A-1-06', 230),
(3162, 'A-1-08', 153),
(3163, 'A-1-13', 108),
(3167, 'A-2-01', 31),
(3168, 'A-2-02', 32),
(3169, 'A-2-04', 32),
(3166, 'A-2-07', 31),
(3173, 'F-1-04', 24),
(3183, 'F-1-06', 14),
(3190, 'F-1-07', 20),
(3182, 'F-1-08', 14),
(3189, 'F-1-09', 20),
(3181, 'F-1-10', 8),
(3188, 'F-1-13', 20),
(3179, 'G-1-03', 24),
(3174, 'G-1-04', 16),
(3178, 'G-1-05', 15),
(3177, 'G-1-07', 22),
(3175, 'G-1-08', 18),
(3176, 'G-1-09', 33),
(3180, 'G-1-10', 22),
(3185, 'I pracownia', 20),
(3186, 'II pracownia', 20),
(3191, 'prac.specjal.', 5),
(3354, 'sala jednostki', 20);

-- --------------------------------------------------------

--
-- Zrzut danych tabeli `users`
--

INSERT INTO `users` (`id`, `login`, `password`, `mail`) VALUES
(1, 'wmakos', '09cbe7d090fca3f9eef103ff4c0df11ff7587b3426b5f1a56b9500a967c21d4d', 'wojciech.makosiej@student.uj.edu.pl'),
(2, 'admin', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918', 'admin@example.com');


-- --------------------------------------------------------

COMMIT;
