import com.google.firebase.database.Exclude
import com.google.firebase.firestore.IgnoreExtraProperties
import java.util.HashMap

@IgnoreExtraProperties
data class Schedules(
    var time: String = "",
    var cpf: String = "",
    var name: String = "",
    var email: String = "",
    var available: Boolean = true
) {
    // Id é uma propriedade mutável para que possa ser alterada pelo Firebase
    var id: String? = null

    // Construtor vazio necessário para usar o Firebase
    constructor() : this("", "", "", "", true)

    // Método para converter para mapa antes de enviar para o Firebase
    @Exclude
    fun toMap(): Map<String, Any?> {
        val result = HashMap<String, Any?>()
        result["time"] = time
        result["cpf"] = cpf
        result["name"] = name
        result["email"] = email
        result["available"] = available
        return result
    }
}
