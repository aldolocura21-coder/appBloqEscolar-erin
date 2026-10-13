package com.example

import com.example.data.EducationalQuestionsRepository
import com.example.model.AvatarConfig
import com.example.model.KidProfile
import com.example.model.Subject
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ExampleUnitTest {
    @Test
    fun testEducationalRepositoryReturnsQuestions() {
        val mathQuestions = EducationalQuestionsRepository.getRandomQuestions(3, Subject.MATH)
        assertEquals(3, mathQuestions.size)
        mathQuestions.forEach {
            assertEquals(Subject.MATH, it.subject)
            assertTrue(it.options.size >= 4)
            assertTrue(it.correctIndex in 0 until it.options.size)
        }
    }

    @Test
    fun testKidProfileLevelCalculation() {
        val profile = KidProfile(xp = 250)
        assertEquals(3, profile.level)
        assertEquals(50, profile.xpInCurrentLevel)
    }

    @Test
    fun testAvatarConfigDefault() {
        val config = AvatarConfig()
        assertEquals(1, config.skinToneIndex)
        assertEquals(0, config.hairStyleIndex)
    }
}
