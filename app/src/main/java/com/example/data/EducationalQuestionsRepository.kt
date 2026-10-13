package com.example.data

import com.example.model.ActivityQuestion
import com.example.model.Subject

object EducationalQuestionsRepository {
    val questions: List<ActivityQuestion> = listOf(
        // Matemáticas
        ActivityQuestion(
            id = "math_1",
            subject = Subject.MATH,
            question = "¿Cuánto es 7 x 8?",
            options = listOf("54", "56", "64", "48"),
            correctIndex = 1,
            explanation = "7 multiplicado por 8 es igual a 56."
        ),
        ActivityQuestion(
            id = "math_2",
            subject = Subject.MATH,
            question = "Si tienes una pizza dividida en 8 partes y comes 3, ¿qué fracción queda?",
            options = listOf("3/8", "5/8", "1/2", "4/8"),
            correctIndex = 1,
            explanation = "Quedan 8 - 3 = 5 porciones de 8, es decir, 5/8."
        ),
        ActivityQuestion(
            id = "math_3",
            subject = Subject.MATH,
            question = "¿Cuál es el perímetro de un cuadrado cuyos lados miden 6 cm?",
            options = listOf("12 cm", "36 cm", "24 cm", "18 cm"),
            correctIndex = 2,
            explanation = "El perímetro de un cuadrado es la suma de sus 4 lados: 4 x 6 = 24 cm."
        ),
        ActivityQuestion(
            id = "math_4",
            subject = Subject.MATH,
            question = "¿Cuál de estos números es un número primo?",
            options = listOf("9", "15", "17", "21"),
            correctIndex = 2,
            explanation = "17 solo es divisible exactamente por 1 y por sí mismo."
        ),
        ActivityQuestion(
            id = "math_5",
            subject = Subject.MATH,
            question = "Si compras un libro de $12 y pagas con un billete de $50, ¿cuánto cambio recibes?",
            options = listOf("$38", "$42", "$36", "$28"),
            correctIndex = 0,
            explanation = "$50 - $12 = $38 de vuelto o cambio."
        ),

        // Lenguaje y Lectura
        ActivityQuestion(
            id = "lang_1",
            subject = Subject.LANGUAGE,
            question = "¿Cuál de las siguientes palabras es un sustantivo?",
            options = listOf("Correr", "Hermoso", "Montaña", "Rápidamente"),
            correctIndex = 2,
            explanation = "'Montaña' es un sustantivo común que nombra un elemento geográfico."
        ),
        ActivityQuestion(
            id = "lang_2",
            subject = Subject.LANGUAGE,
            question = "¿Cuál es el antónimo de la palabra 'Generoso'?",
            options = listOf("Bondadoso", "Tacaño", "Alegre", "Valiente"),
            correctIndex = 1,
            explanation = "'Tacaño' o egoísta es lo contrario de generoso."
        ),
        ActivityQuestion(
            id = "lang_3",
            subject = Subject.LANGUAGE,
            question = "¿Qué tipo de palabra según su acento es 'Árbol'?",
            options = listOf("Aguda", "Grave (o llana)", "Esdrújula", "Sobreesdrújula"),
            correctIndex = 1,
            explanation = "'Árbol' lleva la fuerza de voz en la penúltima sílaba, por lo que es grave o llana."
        ),
        ActivityQuestion(
            id = "lang_4",
            subject = Subject.LANGUAGE,
            question = "¿Cuál es la forma correcta de escribir la palabra?",
            options = listOf("Haber", "Aber", "Haver", "Avér"),
            correctIndex = 0,
            explanation = "El verbo 'haber' se escribe con H inicial y con B."
        ),

        // Ciencias Naturales
        ActivityQuestion(
            id = "sci_1",
            subject = Subject.SCIENCE,
            question = "¿Qué proceso realizan las plantas verdes para fabricar su propio alimento usando luz solar?",
            options = listOf("Respiración", "Fotosíntesis", "Fermentación", "Digestión"),
            correctIndex = 1,
            explanation = "La fotosíntesis transforma dióxido de carbono y agua en glucosa y oxígeno gracias a la clorofila."
        ),
        ActivityQuestion(
            id = "sci_2",
            subject = Subject.SCIENCE,
            question = "¿Cuál es el planeta más grande de nuestro Sistema Solar?",
            options = listOf("Saturno", "Tierra", "Júpiter", "Neptuno"),
            correctIndex = 2,
            explanation = "Júpiter es un gigante gaseoso y el planeta de mayor masa y volumen del Sistema Solar."
        ),
        ActivityQuestion(
            id = "sci_3",
            subject = Subject.SCIENCE,
            question = "¿En qué estado se encuentra el agua cuando se convierte en hielo?",
            options = listOf("Líquido", "Gaseoso", "Sólido", "Plasma"),
            correctIndex = 2,
            explanation = "A temperaturas inferiores a 0 °C el agua se congela en estado sólido."
        ),
        ActivityQuestion(
            id = "sci_4",
            subject = Subject.SCIENCE,
            question = "¿Qué órgano del cuerpo humano bombea la sangre a todo el organismo?",
            options = listOf("El cerebro", "El corazón", "Los pulmones", "El hígado"),
            correctIndex = 1,
            explanation = "El corazón es el músculo vital encargado del bombeo circulatorio."
        ),

        // Historia y Geografía
        ActivityQuestion(
            id = "hist_1",
            subject = Subject.HISTORY,
            question = "¿En qué continente se encuentra la cordillera de los Andes?",
            options = listOf("Europa", "Asia", "América", "África"),
            correctIndex = 2,
            explanation = "La cordillera de los Andes recorre la parte occidental de América del Sur."
        ),
        ActivityQuestion(
            id = "hist_2",
            subject = Subject.HISTORY,
            question = "¿Cuál es el río más caudaloso del mundo?",
            options = listOf("Río Nilo", "Río Amazonas", "Río Misisipi", "Río Yangtsé"),
            correctIndex = 1,
            explanation = "El río Amazonas en Sudamérica es el más caudaloso del planeta."
        ),
        ActivityQuestion(
            id = "hist_3",
            subject = Subject.HISTORY,
            question = "¿Qué civilización antigua construyó las pirámides de Guiza?",
            options = listOf("Los Romanos", "Los Egipcios", "Los Griegos", "Los Mayas"),
            correctIndex = 1,
            explanation = "Fueron construidas por la civilización del Antiguo Egipto."
        ),

        // Lógica y Acertijos
        ActivityQuestion(
            id = "logic_1",
            subject = Subject.LOGIC,
            question = "¿Qué número sigue en la serie: 2, 4, 8, 16, ...?",
            options = listOf("24", "30", "32", "20"),
            correctIndex = 2,
            explanation = "Cada número es el doble del anterior: 16 x 2 = 32."
        ),
        ActivityQuestion(
            id = "logic_2",
            subject = Subject.LOGIC,
            question = "Si un tren eléctrico viaja hacia el norte, ¿hacia dónde va el humo?",
            options = listOf("Al sur", "Al norte", "A ninguna parte", "Al este"),
            correctIndex = 2,
            explanation = "¡Los trenes eléctricos no emiten humo!"
        ),
        ActivityQuestion(
            id = "logic_3",
            subject = Subject.LOGIC,
            question = "La madre de María tiene 4 hijas: Primavera, Verano, Otoño y... ¿cómo se llama la cuarta?",
            options = listOf("Invierno", "María", "Abril", "Rosa"),
            correctIndex = 1,
            explanation = "La cuarta hija es María, como indica el inicio del acertijo."
        )
    )

    fun getRandomQuestions(count: Int = 3, subjectFilter: Subject? = null): List<ActivityQuestion> {
        val pool = if (subjectFilter != null && subjectFilter != Subject.AI_SPECIAL) {
            questions.filter { it.subject == subjectFilter }
        } else {
            questions
        }
        return pool.shuffled().take(count)
    }
}
