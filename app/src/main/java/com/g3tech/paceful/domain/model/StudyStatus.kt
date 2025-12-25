package com.g3tech.paceful.domain.model

enum class StudyStatus(val id: Long) {
    PENDING(1),
    IN_PROGRESS(2),
    DONE(3),
    SCHEDULED(4);
}