package com.example.productcatalog

import com.example.productcatalog.data.model.ProductDto
import com.example.productcatalog.data.model.toDomain
import org.junit.Assert.assertEquals
import org.junit.Test

class ProductDtoTest {

    @Test
    fun `toDomain maps fields correctly and handles nulls`() {
        val dto = ProductDto(
            id = 1,
            title = "Test Product",
            description = null,
            price = 10.0,
            rating = null,
            thumbnail = "thumb.jpg",
            images = null
        )

        val domain = dto.toDomain()

        assertEquals(1, domain.id)
        assertEquals("Test Product", domain.title)
        assertEquals("", domain.description)
        assertEquals(10.0, domain.price, 0.0)
        assertEquals(0.0, domain.rating, 0.0)
        assertEquals("thumb.jpg", domain.thumbnail)
        assertEquals(emptyList<String>(), domain.images)
    }
}
