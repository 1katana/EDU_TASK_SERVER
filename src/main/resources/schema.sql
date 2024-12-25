

-- Создаем схему если она не существует
CREATE SCHEMA IF NOT EXISTS edu_task;




-- Переключаемся на схему edu_task
SET search_path TO edu_task;





-- Создаем таблицу пользователей
CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY,                          -- Уникальный идентификатор пользователя
    name VARCHAR(255) NOT NULL,                      -- Имя пользователя
    last_name VARCHAR(255),                          -- Фамилия пользователя
    email VARCHAR(255) UNIQUE NOT NULL,              -- Email пользователя
    password VARCHAR(1024) NOT NULL,                 -- Хэшированный пароль
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP -- Дата создания записи
);

-- Обновляем таблицу групп
CREATE TABLE IF NOT EXISTS groups (
    id BIGSERIAL PRIMARY KEY,                        -- Уникальный идентификатор группы
    name VARCHAR(255) NOT NULL,                      -- Название группы
    creator_id BIGINT NOT NULL,                     -- ID пользователя, создавшего группу
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP, -- Дата создания группы
    FOREIGN KEY (creator_id) REFERENCES users(id) ON DELETE CASCADE -- Связь с таблицей users
);

-- Создаем таблицу для связи "группа-ученик"
CREATE TABLE IF NOT EXISTS group_users (
    group_id BIGINT NOT NULL,                       -- ID группы
    user_id BIGINT NOT NULL,                        -- ID пользователя (ученик)
    PRIMARY KEY (group_id, user_id),                 -- Уникальная пара "группа-пользователь"
    FOREIGN KEY (group_id) REFERENCES groups(id) ON DELETE CASCADE, -- Связь с таблицей groups
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE  -- Связь с таблицей users
);

-- Создаем таблицу задач
CREATE TABLE IF NOT EXISTS tasks (
    id BIGSERIAL PRIMARY KEY,                       -- Уникальный идентификатор задачи
    title VARCHAR(255) NOT NULL,                   -- Заголовок задачи
    description TEXT,                              -- Описание задачи
    deadline TIMESTAMP WITH TIME ZONE NOT NULL,    -- Дедлайн задачи
    group_id BIGINT NOT NULL,                      -- ID группы, для которой предназначена задача
    creator_id BIGINT NOT NULL,                    -- ID пользователя, создавшего задачу
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP, -- Дата создания задачи
    FOREIGN KEY (group_id) REFERENCES groups(id) ON DELETE CASCADE, -- Связь с таблицей groups
    FOREIGN KEY (creator_id) REFERENCES users(id) ON DELETE CASCADE  -- Связь с таблицей users
);

-- Создаем таблицу индивидуальных статусов задач
CREATE TABLE IF NOT EXISTS task_statuses (
    id BIGSERIAL PRIMARY KEY,                      -- Уникальный идентификатор записи
    task_id BIGINT NOT NULL,                       -- ID задачи
    user_id BIGINT NOT NULL,                       -- ID ученика
    status VARCHAR(50) CHECK (status IN ('NEW', 'IN_PROGRESS', 'DONE')) DEFAULT 'NEW', -- Статус задачи
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP, -- Дата последнего обновления статуса
    FOREIGN KEY (task_id) REFERENCES tasks(id) ON DELETE CASCADE, -- Связь с таблицей tasks
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE, -- Связь с таблицей users
    UNIQUE (task_id, user_id)                      -- Уникальная комбинация "задача-ученик"
);
