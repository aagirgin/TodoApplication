package com.example.todoapp.domain.model

data class UserActivities(
    var isDone: CompletedStatus = CompletedStatus.INCOMPLETE,
    val activityName: String
)

enum class CompletedStatus {
    COMPLETED,
    INCOMPLETE
}