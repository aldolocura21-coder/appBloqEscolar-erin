package com.example.service

import com.example.BuildConfig
import com.example.data.EducationalQuestionsRepository
import com.example.model.ActivityQuestion
import com.example.model.Subject
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query
import java.util.UUID
import java.util.concurrent.TimeUnit

@JsonClass(generateAdapter = true)
data class GeminiPart(
    val text: String? = null
)

@JsonClass(generateAdapter = true)
data class GeminiContent(
    val parts: List<GeminiPart>
)

@JsonClass(generateAdapter = true)
data class GeminiRequest(
    val contents: List<GeminiContent>
)

@JsonClass(generateAdapter = true)
data class GeminiCandidate(
    val content: GeminiContent?
)

@JsonClass(generateAdapter = true)
data class GeminiResponse(
    val candidates: List<GeminiCandidate>?
)

interface GeminiApiService {
    @POST("v1beta/models/gemini-3.5-flash:generateContent")
    suspend fun generateContent(
        @Query("key") apiKey: String,
        @Body request: GeminiRequest
    ): GeminiResponse
}

object GeminiAiService {
    private val moshi: Moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://generativelanguage.googleapis.com/")
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    private val apiService: GeminiApiService = retrofit.create(GeminiApiService::class.java)

    suspend fun generateQuestions(
        subject: Subject,
        gradeLevel: String,
        age: Int = 10,
        count: Int = 3
    ): List<ActivityQuestion> = withContext(Dispatchers.IO) {
        val apiKey = try {
            BuildConfig.GEMINI_API_KEY
        } catch (_: Exception) {
            ""
        }

        if (apiKey.isNullOrBlank()) {
            return@withContext EducationalQuestionsRepository.getRandomQuestions(count, subject)
        }

        val subjectContext = if (subject == Subject.FAMILY) {
            "El tema es 'Interacción en Familia con Adultos'. Las preguntas deben ser retos para que el niño/a dialogue, pregunte o interactúe con un adulto (mamá, papá o familiar) como acertijos compartidos, preguntas de su infancia o cálculo mental conjunto."
        } else {
            "Genera preguntas educativas para la materia '${subject.displayName}', con dificultad adaptada perfectamente a un niño/a de $age años ($gradeLevel)."
        }

        val prompt = """
            Eres un profesor pedagógico experto para un estudiante de $age años (Nivel educativo escolar: $gradeLevel).
            $subjectContext
            Devuelve ÚNICAMENTE un formato JSON estricto con la siguiente estructura y sin bloques de código markdown:
            [
              {
                "question": "¿Pregunta o reto interactivo adaptado a $age años?",
                "options": ["Opción A", "Opción B", "Opción C", "Opción D"],
                "correctIndex": 0,
                "explanation": "Breve explicación pedagógica y motivadora para el alumno."
              }
            ]
        """.trimIndent()

        try {
            val request = GeminiRequest(
                contents = listOf(
                    GeminiContent(parts = listOf(GeminiPart(text = prompt)))
                )
            )
            val response = apiService.generateContent(apiKey = apiKey, request = request)
            val textOutput = response.candidates?.firstOrNull()?.content?.parts?.firstOrNull()?.text

            if (!textOutput.isNullOrBlank()) {
                val parsed = parseQuestionsFromJson(textOutput, subject)
                if (parsed.isNotEmpty()) {
                    return@withContext parsed
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        // Graceful fallback
        return@withContext EducationalQuestionsRepository.getRandomQuestions(count, subject)
    }

    private fun parseQuestionsFromJson(rawJson: String, subject: Subject): List<ActivityQuestion> {
        val clean = rawJson.replace("```json", "").replace("```", "").trim()
        val startIndex = clean.indexOf('[')
        val endIndex = clean.lastIndexOf(']')
        if (startIndex == -1 || endIndex == -1 || endIndex <= startIndex) {
            return emptyList()
        }
        val arrayJson = clean.substring(startIndex, endIndex + 1)

        return try {
            val type = com.squareup.moshi.Types.newParameterizedType(
                List::class.java,
                Map::class.java
            )
            val adapter = moshi.adapter<List<Map<String, Any>>>(type)
            val list = adapter.fromJson(arrayJson) ?: return emptyList()

            list.mapNotNull { item ->
                val q = item["question"] as? String ?: return@mapNotNull null
                @Suppress("UNCHECKED_CAST")
                val opts = (item["options"] as? List<*>)?.map { it.toString() } ?: return@mapNotNull null
                val correct = (item["correctIndex"] as? Number)?.toInt() ?: 0
                val expl = item["explanation"] as? String ?: "¡Excelente trabajo!"
                ActivityQuestion(
                    id = "ai_" + UUID.randomUUID().toString().take(8),
                    subject = subject,
                    question = q,
                    options = opts,
                    correctIndex = correct.coerceIn(0, opts.lastIndex.coerceAtLeast(0)),
                    explanation = expl
                )
            }
        } catch (_: Exception) {
            emptyList()
        }
    }
}
