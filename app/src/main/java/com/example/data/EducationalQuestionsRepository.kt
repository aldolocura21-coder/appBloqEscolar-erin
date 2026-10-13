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
        ),

        // Actividades en Familia e Interacción con Adultos (Día por medio)
        ActivityQuestion(
            id = "fam_1",
            subject = Subject.FAMILY,
            question = "👨‍👩‍👧‍👦 Pregúntale a un adulto: ¿A qué jugaba al aire libre cuando tenía tu edad? Conversen y elijan una respuesta.",
            options = listOf("Ya conversamos y me contó su juego de infancia", "Me enseñó a qué jugaba y sus anécdotas", "Hablamos de cómo jugaban sin móviles", "Descubrí a qué le gustaba jugar"),
            correctIndex = 0,
            explanation = "¡Hermoso momento compartido! Aprender de las infancias de nuestros padres o familiares fortalece los lazos familiares.",
            xpReward = 35,
            coinsReward = 15
        ),
        ActivityQuestion(
            id = "fam_2",
            subject = Subject.FAMILY,
            question = "👨‍👩‍👧‍👦 Cálculo mental en equipo con un adulto: Si sumas tu edad actual más la edad del adulto, ¿cuántos años suman entre los dos?",
            options = listOf("Ya calculamos la suma de nuestras dos edades", "Calculamos la diferencia de años entre ambos", "El adulto me ayudó con la suma mental", "Sumamos y nos dio un número exacto"),
            correctIndex = 0,
            explanation = "¡Gran trabajo en equipo! Las matemáticas se disfrutan mucho más compartiendo el cálculo en familia.",
            xpReward = 35,
            coinsReward = 15
        ),
        ActivityQuestion(
            id = "fam_3",
            subject = Subject.FAMILY,
            question = "👨‍👩‍👧‍👦 Pide a un adulto que te enseñe un refrán o dicho popular que conozca y te explique qué significa.",
            options = listOf("Me enseñó un refrán y comprendí su significado", "Conversamos sobre la sabiduría de los refranes", "El adulto recordó un consejo de sus abuelos", "Compartimos un dicho popular tradicional"),
            correctIndex = 0,
            explanation = "Los refranes y consejos populares transmiten sabiduría que pasa de generación en generación.",
            xpReward = 35,
            coinsReward = 15
        ),
        ActivityQuestion(
            id = "fam_4",
            subject = Subject.FAMILY,
            question = "👨‍👩‍👧‍👦 Pregúntale a un adulto: ¿Cuál era su asignatura o materia favorita en la escuela y por qué?",
            options = listOf("Ya me contó cuál era su materia favorita", "Me explicó por qué le apasionaba esa materia", "Conversamos de sus profesores y materias", "Comparamos sus materias con las mías hoy"),
            correctIndex = 0,
            explanation = "¡Genial! Conocer las materias favoritas de los adultos te ayuda a descubrir nuevas curiosidades e intereses escolares.",
            xpReward = 35,
            coinsReward = 15
        ),
        ActivityQuestion(
            id = "fam_5",
            subject = Subject.FAMILY,
            question = "👨‍👩‍👧‍👦 Conversación en familia: Pregúntale a un adulto cómo era la vida diaria cuando no existían los smartphones.",
            options = listOf("Me contó cómo se comunicaban sin celulares", "Me explicó cómo buscaban información en libros", "Conversamos de cómo se divertían antes", "Comprendí cómo cambiaron las costumbres"),
            correctIndex = 0,
            explanation = "¡Qué interesante! Valorar la tecnología actual y recordar las tradiciones pasadas es una gran enseñanza familiar.",
            xpReward = 35,
            coinsReward = 15
        ),
        ActivityQuestion(
            id = "fam_6",
            subject = Subject.FAMILY,
            question = "👨‍👩‍👧‍👦 Gratitud y empatía familiar: Cada uno (tú y el adulto) debe contar una cosa buena que le pasó en el día.",
            options = listOf("Compartimos ambos algo positivo de nuestro día", "Nos agradecimos mutuamente un gesto lindo", "Fue un momento muy cálido y alegre", "Nos alegramos de escucharnos"),
            correctIndex = 0,
            explanation = "La gratitud y escuchar cómo le fue al otro crea un ambiente de confianza y cariño en casa.",
            xpReward = 35,
            coinsReward = 15
        ),

        // Robótica y Programación
        ActivityQuestion(
            id = "rob_1",
            subject = Subject.ROBOTICS,
            question = "¿Qué componente permite a un robot percibir el entorno que lo rodea (como la luz, la distancia o la temperatura)?",
            options = listOf("Un actuador", "Un sensor", "Una rueda", "Un motor"),
            correctIndex = 1,
            explanation = "Los sensores son los 'sentidos' del robot; captan datos del entorno físico y los envían al controlador."
        ),
        ActivityQuestion(
            id = "rob_2",
            subject = Subject.ROBOTICS,
            question = "En robótica y programación, ¿qué es un servomotor?",
            options = listOf("Un motor que gira a velocidad fija", "Un motor que permite controlar con precisión la posición y el ángulo", "Una batería recargable", "Un cable de conexión"),
            correctIndex = 1,
            explanation = "Los servomotores permiten posicionar brazos o articulaciones de robots en ángulos exactos (por ejemplo, entre 0° y 180°)."
        ),
        ActivityQuestion(
            id = "rob_3",
            subject = Subject.ROBOTICS,
            question = "¿Qué es un 'algoritmo' en programación y robótica?",
            options = listOf("Un virus informático", "Un conjunto ordenado de instrucciones paso a paso para resolver un problema", "Un tipo de pantalla táctil", "Un chip de memoria RAM"),
            correctIndex = 1,
            explanation = "Un algoritmo es una secuencia lógica y finita de pasos o instrucciones para cumplir una tarea determinada."
        ),
        ActivityQuestion(
            id = "rob_4",
            subject = Subject.ROBOTICS,
            question = "¿Qué placa electrónica muy utilizada en escuelas secundarias y talleres sirve para controlar robots programando en Arduino o bloques?",
            options = listOf("Placa de yeso", "Microcontrolador Arduino / micro:bit", "Tarjeta de video gamer", "Disco duro mecánico"),
            correctIndex = 1,
            explanation = "Arduino y BBC micro:bit son microcontroladores educativos ideales para aprender robótica y automatización."
        ),

        // Informática y Computación
        ActivityQuestion(
            id = "inf_1",
            subject = Subject.INFORMATICS,
            question = "¿Cuál de los siguientes es un componente de 'hardware' de una computadora?",
            options = listOf("El navegador web Google Chrome", "El sistema operativo Windows", "El teclado y el procesador", "Un videojuego"),
            correctIndex = 2,
            explanation = "El hardware es la parte física y tangible del equipo (teclado, mouse, procesador, disco)."
        ),
        ActivityQuestion(
            id = "inf_2",
            subject = Subject.INFORMATICS,
            question = "¿Qué medida de seguridad es fundamental para proteger tus cuentas en internet?",
            options = listOf("Usar siempre 1234 como clave", "Compartir la contraseña con amigos", "Usar contraseñas largas y no compartirlas", "Anotar la clave en un cartel público"),
            correctIndex = 2,
            explanation = "Tener contraseñas seguras, únicas y privadas previene accesos no autorizados a tu información."
        ),
        ActivityQuestion(
            id = "inf_3",
            subject = Subject.INFORMATICS,
            question = "¿Cuál es la función principal de la memoria RAM en una computadora o teléfono celular?",
            options = listOf("Guardar las fotos para siempre", "Almacenar temporalmente los datos y programas que se están ejecutando", "Enfriar la batería", "Aumentar el volumen del parlante"),
            correctIndex = 1,
            explanation = "La memoria RAM almacena temporalmente los datos de las aplicaciones abiertas para un acceso ultrarrápido."
        ),
        ActivityQuestion(
            id = "inf_4",
            subject = Subject.INFORMATICS,
            question = "¿Qué tipo de archivo suele tener la extensión '.png' o '.jpg'?",
            options = listOf("Un archivo de audio", "Una imagen o fotografía", "Un archivo de texto sin formato", "Un programa instalable"),
            correctIndex = 1,
            explanation = ".png y .jpg son formatos universales para almacenar imágenes y fotografías digitales."
        ),

        // Inteligencia Artificial
        ActivityQuestion(
            id = "ai_1",
            subject = Subject.ARTIFICIAL_INTELLIGENCE,
            question = "¿Cómo aprenden los sistemas modernos de Inteligencia Artificial (Machine Learning)?",
            options = listOf("Memorizando un libro de papel", "Entrenándose con grandes volúmenes de datos y ejemplos", "Durmiendo por las noches", "Mediante magia digital"),
            correctIndex = 1,
            explanation = "El aprendizaje automático (Machine Learning) entrena algoritmos con miles o millones de ejemplos de datos para detectar patrones."
        ),
        ActivityQuestion(
            id = "ai_2",
            subject = Subject.ARTIFICIAL_INTELLIGENCE,
            question = "¿Cuál de estas aplicaciones cotidianas utiliza Inteligencia Artificial?",
            options = listOf("Una regla de plástico", "El reconocimiento facial para desbloquear el celular", "Un cuaderno escolar", "Un lápiz negro"),
            correctIndex = 1,
            explanation = "El reconocimiento facial utiliza redes neuronales artificiales de visión por computadora para identificar rostros."
        ),
        ActivityQuestion(
            id = "ai_3",
            subject = Subject.ARTIFICIAL_INTELLIGENCE,
            question = "¿Qué es un 'Prompt' en los asistentes de Inteligencia Artificial generativa?",
            options = listOf("El cable de corriente", "La instrucción o texto que el usuario escribe para pedirle algo a la IA", "Un botón de apagado", "La pantalla de la computadora"),
            correctIndex = 1,
            explanation = "El prompt es el mensaje, consigna o pregunta que le das a la IA para guiar su respuesta o generación de contenido."
        ),
        ActivityQuestion(
            id = "ai_4",
            subject = Subject.ARTIFICIAL_INTELLIGENCE,
            question = "¿Por qué es importante verificar la información que nos da una Inteligencia Artificial?",
            options = listOf("Porque las IA pueden 'alucinar' o cometer errores con datos no contrastados", "Porque las IA nunca se equivocan", "Porque borran la memoria", "Porque solo hablan en latín"),
            correctIndex = 0,
            explanation = "Las inteligencias artificiales pueden generar información incorrecta (alucinaciones), por lo que siempre se debe chequear con fuentes confiables."
        ),

        // Idioma Extranjero (Inglés)
        ActivityQuestion(
            id = "eng_1",
            subject = Subject.ENGLISH,
            question = "How do you say 'Buenos días' in English?",
            options = listOf("Good night", "Good afternoon", "Good morning", "Goodbye"),
            correctIndex = 2,
            explanation = "'Good morning' significa buenos días en inglés."
        ),
        ActivityQuestion(
            id = "eng_2",
            subject = Subject.ENGLISH,
            question = "Which of the following words means 'Biblioteca' in English?",
            options = listOf("Bookstore", "Library", "Kitchen", "Hospital"),
            correctIndex = 1,
            explanation = "'Library' significa biblioteca. Cuidado: 'Bookstore' es una librería comercial donde se compran libros."
        ),
        ActivityQuestion(
            id = "eng_3",
            subject = Subject.ENGLISH,
            question = "Complete the sentence: 'She _____ reading an interesting book.'",
            options = listOf("is", "are", "am", "be"),
            correctIndex = 0,
            explanation = "Con la tercera persona del singular (He/She/It) del verbo to be se utiliza 'is'."
        ),
        ActivityQuestion(
            id = "eng_4",
            subject = Subject.ENGLISH,
            question = "What is the plural form of the word 'Child' (niño/niña)?",
            options = listOf("Childs", "Children", "Childes", "Childrens"),
            correctIndex = 1,
            explanation = "'Child' tiene un plural irregular en inglés: 'Children'."
        ),

        // Educación Física y Deportes
        ActivityQuestion(
            id = "pe_1",
            subject = Subject.PHYSICAL_EDUCATION,
            question = "¿Por qué es fundamental realizar una entrada en calor antes de hacer actividad física intensa?",
            options = listOf("Para cansar los músculos rápido", "Para preparar el sistema cardiovascular y prevenir lesiones musculares", "Para enfriar el cuerpo", "Para gastar agua"),
            correctIndex = 1,
            explanation = "La entrada en calor aumenta la temperatura corporal y el flujo sanguíneo a los músculos, previniendo calambres y desgarros."
        ),
        ActivityQuestion(
            id = "pe_2",
            subject = Subject.PHYSICAL_EDUCATION,
            question = "En básquetbol o baloncesto, ¿cuántos puntos vale una canasta convertida desde detrás de la línea más lejana?",
            options = listOf("1 punto", "2 puntos", "3 puntos", "4 puntos"),
            correctIndex = 2,
            explanation = "Los lanzamientos encestados más allá de la línea de 6,75 m otorgan 3 puntos."
        ),
        ActivityQuestion(
            id = "pe_3",
            subject = Subject.PHYSICAL_EDUCATION,
            question = "¿Cuál es el principal líquido recomendado para hidratarse antes, durante y después del ejercicio escolar?",
            options = listOf("Bebidas azucaradas o gaseosas", "Agua potable", "Té con leche muy caliente", "Jugo concentrado con colorantes"),
            correctIndex = 1,
            explanation = "El agua potable es el hidratante natural y más saludable por excelencia para el organismo."
        ),
        ActivityQuestion(
            id = "pe_4",
            subject = Subject.PHYSICAL_EDUCATION,
            question = "¿En qué consiste el principio del 'Fair Play' o Juego Limpio en el deporte?",
            options = listOf("Ganar cometiendo infracciones a escondidas", "Respetar las reglas, a los compañeros, a los rivales y a los árbitros", "Protestar todas las decisiones", "No permitir que jueguen todos"),
            correctIndex = 1,
            explanation = "El Juego Limpio promueve la honestidad deportiva, el compañerismo y el respeto mutuo en toda competencia."
        ),

        // Historia Argentina
        ActivityQuestion(
            id = "harg_1",
            subject = Subject.ARGENTINE_HISTORY,
            question = "¿Qué acontecimiento histórico se conmemora en Argentina el 25 de mayo de 1810?",
            options = listOf("La Declaración de la Independencia", "La Revolución de Mayo y la formación del Primer Gobierno Patrio", "El Cruce de los Andes", "El Día de la Bandera"),
            correctIndex = 1,
            explanation = "El 25 de mayo de 1810 se formó la Primera Junta de Gobierno en el Cabildo de Buenos Aires."
        ),
        ActivityQuestion(
            id = "harg_2",
            subject = Subject.ARGENTINE_HISTORY,
            question = "¿En qué ciudad y año se declaró formalmente la Independencia de las Provincias Unidas del Río de la Plata?",
            options = listOf("En Córdoba en 1820", "En San Miguel de Tucumán el 9 de julio de 1816", "En Buenos Aires en 1810", "En Rosario en 1812"),
            correctIndex = 1,
            explanation = "El 9 de julio de 1816 en la histórica Casa de Tucumán el Congreso declaró la Independencia."
        ),
        ActivityQuestion(
            id = "harg_3",
            subject = Subject.ARGENTINE_HISTORY,
            question = "¿Qué prócer argentino creó la Bandera Nacional a orillas del río Paraná en 1812?",
            options = listOf("José de San Martín", "Manuel Belgrano", "Mariano Moreno", "Domingo Faustino Sarmiento"),
            correctIndex = 1,
            explanation = "Manuel Belgrano izó por primera vez la Bandera Nacional celeste y blanca en Rosario el 27 de febrero de 1812."
        ),
        ActivityQuestion(
            id = "harg_4",
            subject = Subject.ARGENTINE_HISTORY,
            question = "¿Qué gesta militar lideró el General José de San Martín en 1817 para liberar a Chile y Perú?",
            options = listOf("La Vuelta de Obligado", "El Cruce de los Andes con el Ejército de los Andes", "La Batalla de Caseros", "La Conquista del Desierto"),
            correctIndex = 1,
            explanation = "El Cruce de la cordillera de los Andes es una de las mayores hazañas militares y emancipadoras de la historia universal."
        ),

        // Construcción de Ciudadanía y Ética
        ActivityQuestion(
            id = "cit_1",
            subject = Subject.CITIZENSHIP,
            question = "¿Cuál es la ley suprema y máxima norma jurídica de la República Argentina?",
            options = listOf("El Código de Tránsito", "La Constitución Nacional Argentina", "El reglamento escolar", "La carta magna provincial"),
            correctIndex = 1,
            explanation = "La Constitución Nacional es la ley fundamental que organiza el Estado argentino y consagra los derechos de todos los habitantes."
        ),
        ActivityQuestion(
            id = "cit_2",
            subject = Subject.CITIZENSHIP,
            question = "En el sistema democrático y republicano argentino, ¿cuáles son los tres poderes de gobierno?",
            options = listOf("Poder Policial, Militar y Escolar", "Poder Ejecutivo, Legislativo y Judicial", "Poder Municipal, Barrial y Vecinal", "Poder Deportivo, Cultural y Científico"),
            correctIndex = 1,
            explanation = "El sistema republicano divide el poder en Ejecutivo (administra), Legislativo (crea leyes) y Judicial (aplica justicia)."
        ),
        ActivityQuestion(
            id = "cit_3",
            subject = Subject.CITIZENSHIP,
            question = "¿Cuál de los siguientes es un derecho fundamental garantizado por la Convención sobre los Derechos del Niño?",
            options = listOf("Derecho a la educación, a la salud y a una identidad", "Derecho a no asistir a la escuela nunca", "Derecho a trabajar antes de los 10 años", "Derecho a no tener nombre ni DNI"),
            correctIndex = 0,
            explanation = "Los niños tienen derechos humanos inalienables: derecho a tener un nombre, nacionalidad, salud, educación y ser protegidos."
        ),

        // Física y Química
        ActivityQuestion(
            id = "phy_1",
            subject = Subject.PHYSICS_CHEMISTRY,
            question = "¿Qué partículas con carga negativa orbitan alrededor del núcleo de un átomo?",
            options = listOf("Protones", "Neutrones", "Electrones", "Fotones"),
            correctIndex = 2,
            explanation = "Los electrones tienen carga eléctrica negativa y se encuentran en la corteza orbital del átomo."
        ),
        ActivityQuestion(
            id = "phy_2",
            subject = Subject.PHYSICS_CHEMISTRY,
            question = "¿Cuál de las siguientes es una mezcla homogénea (solución)?",
            options = listOf("Agua con arena", "Agua con sal completamente disuelta", "Ensalada de frutas", "Aceite con agua"),
            correctIndex = 1,
            explanation = "El agua con sal disuelta forma una sola fase observable, por lo que es una mezcla homogénea o solución."
        ),
        ActivityQuestion(
            id = "phy_3",
            subject = Subject.PHYSICS_CHEMISTRY,
            question = "¿Qué fuerza descubierta por Isaac Newton hace que los objetos caigan hacia el centro de la Tierra?",
            options = listOf("Fuerza centrífuga", "Fuerza de gravedad", "Fuerza electrostática", "Fuerza de rozamiento del aire"),
            correctIndex = 1,
            explanation = "La fuerza de gravedad es la atracción mutua que ejerce la masa de la Tierra sobre todos los cuerpos."
        ),

        // Educación Artística y Música
        ActivityQuestion(
            id = "art_1",
            subject = Subject.ART_MUSIC,
            question = "¿Cuáles son los tres colores primarios tradicionales en las artes plásticas?",
            options = listOf("Verde, Naranja y Violeta", "Rojo, Amarillo y Azul", "Blanco, Negro y Gris", "Rosa, Celeste y Marrón"),
            correctIndex = 1,
            explanation = "Rojo, amarillo y azul son los colores primarios a partir de los cuales se mezclan los colores secundarios."
        ),
        ActivityQuestion(
            id = "art_2",
            subject = Subject.ART_MUSIC,
            question = "¿Qué instrumento de percusión tradicional argentino se fabrica con madera ahuecada y parches de cuero de oveja o cabra?",
            options = listOf("El violín", "El bombo legüero", "El saxofón", "El piano"),
            correctIndex = 1,
            explanation = "El bombo legüero es el corazón rítmico del folklore argentino tradicional (chacarera, zamba)."
        ),

        // Ecología y Medio Ambiente
        ActivityQuestion(
            id = "eco_1",
            subject = Subject.ENVIRONMENT,
            question = "¿Qué significan las '3R' fundamentales de la ecología y el cuidado ambiental?",
            options = listOf("Romper, Rayar y Reparar", "Reducir, Reutilizar y Reciclar", "Rápido, Raro y Real", "Reclamar, Reír y Respirar"),
            correctIndex = 1,
            explanation = "Reducir el consumo innecesario, reutilizar los objetos y reciclar los materiales son claves ecológicas."
        ),
        ActivityQuestion(
            id = "eco_2",
            subject = Subject.ENVIRONMENT,
            question = "¿Cuál es el felino autóctono más grande de Argentina, declarado Monumento Natural Nacional en peligro de extinción?",
            options = listOf("El león africano", "El Yaguareté", "El gato doméstico", "El guepardo"),
            correctIndex = 1,
            explanation = "El Yaguareté es el mayor depredador terrestre de América del Sur y habita en el norte argentino (Yungas, Chaco y Misiones)."
        ),

        // Educación Financiera
        ActivityQuestion(
            id = "fin_1",
            subject = Subject.FINANCIAL_EDUCATION,
            question = "¿Cuál es la diferencia entre una 'necesidad' y un 'deseo' al planificar los gastos personales?",
            options = listOf("Son exactamente lo mismo", "Una necesidad es indispensable para vivir (alimento, salud, techo) y un deseo es algo que nos gustaría tener", "Un deseo siempre es más barato que una necesidad", "Las necesidades nunca cuestan dinero"),
            correctIndex = 1,
            explanation = "Saber distinguir entre lo imprescindible (necesidad) y lo optativo (deseo) es la base de un presupuesto inteligente."
        ),
        ActivityQuestion(
            id = "fin_2",
            subject = Subject.FINANCIAL_EDUCATION,
            question = "¿Qué es el 'ahorro'?",
            options = listOf("Gastar todo el dinero apenas se recibe", "Guardar y reservar una parte de los ingresos para metas futuras o emergencias", "Pedir dinero prestado con intereses altos", "Perder monedas en la calle"),
            correctIndex = 1,
            explanation = "El ahorro consiste en reservar una porción de nuestros recursos económicos para proyectos futuros o imprevistos."
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
