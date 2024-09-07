package dnd11th.blooming.common.util

import java.sql.Connection
import java.sql.DriverManager
import java.sql.PreparedStatement

fun main() {
    val url = System.getenv("DB_URL")
    val username = System.getenv("DB_USERNAME")
    val password = System.getenv("DB_PASSWORD")

    if (url.isBlank() || username.isBlank() || password.isBlank()) {
        throw IllegalStateException("데이터베이스 연결을 위한 환경변수를 설정해주세요.")
    }

    var connection: Connection? = null
    var selectStatement: PreparedStatement? = null
    var updateStatement: PreparedStatement? = null

    try {
        connection = DriverManager.getConnection(url, username, password)

        val selectQuery = "SELECT id, kor_name FROM plant"
        selectStatement = connection.prepareStatement(selectQuery)

        val updateQuery = "UPDATE plant SET decomposed_kor_name = ? WHERE id = ?"
        updateStatement = connection.prepareStatement(updateQuery)

        val resultSet = selectStatement.executeQuery()

        while (resultSet.next()) {
            val id = resultSet.getInt("id")
            val korName = resultSet.getString("kor_name")

            val decomposedHangul = korName.toDecomposedHangul()

            updateStatement.setString(1, decomposedHangul)
            updateStatement.setInt(2, id)
            updateStatement.executeUpdate()
        }
    } catch (e: Exception) {
        e.printStackTrace()
    } finally {
        updateStatement?.close()
        selectStatement?.close()
        connection?.close()
    }
}
