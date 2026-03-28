package com.testingpractice.duoclonebackend.progress.api.controller

import com.testingpractice.duoclonebackend.commons.constants.pathConstants
import com.testingpractice.duoclonebackend.progress.api.dto.LessonCompleteRequest
import com.testingpractice.duoclonebackend.progress.api.dto.LessonCompleteResponse
import com.testingpractice.duoclonebackend.progress.app.service.LessonCompletionService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(pathConstants.LESSONS_COMPLETIONS)
class LessonCompletionController(
    private val lessonCompletionService: LessonCompletionService
) {

    @PostMapping(pathConstants.SUBMIT_COMPLETED_LESSON)
    fun completeLesson(
        @RequestBody lessonCompleteRequest: LessonCompleteRequest,
        @AuthenticationPrincipal(expression = "id") userId: Int
    ): org.springframework.http.ResponseEntity<Any> {
        return try {
            val response = lessonCompletionService.getCompletedLesson(
                lessonCompleteRequest.lessonId,
                userId,
                lessonCompleteRequest.courseId
            )
            org.springframework.http.ResponseEntity.ok(response)
        } catch (e: com.testingpractice.duoclonebackend.commons.exception.ApiException) {
            org.springframework.http.ResponseEntity.status(org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR)
                .body(mapOf("error" to "ApiException", "message" to e.message, "code" to e.code.name))
        } catch (e: Exception) {
            org.springframework.http.ResponseEntity.status(org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR)
                .body(mapOf("error" to "Exception", "message" to e.message))
        }
    }
}