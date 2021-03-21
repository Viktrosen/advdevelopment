package advdevelopment.com.repository

interface DataSource<T> {

    suspend fun getData(word: String): T
}
