package io.buildnote.gradle.plugin.util

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi.Builder
import org.http4k.format.AutoMappingConfiguration
import org.http4k.format.ConfigurableMoshi
import org.http4k.format.EventAdapter
import org.http4k.format.ListAdapter
import org.http4k.format.MapAdapter
import org.http4k.format.asConfigurable
import org.http4k.format.text
import org.http4k.format.value
import org.http4k.format.withStandardMappings
import se.ansman.kotshi.KotshiJsonAdapterFactory
import java.math.BigDecimal

object EventsJson : ConfigurableMoshi(
    Builder()
        .add(EventAdapter)
        .add(ListAdapter)
        .add(EventsJsonAdapterFactory)
        .addLast(MapAdapter)
        .asConfigurable()
        .withStandardMappings()
        .withEventsMapping()
        .done()
)

@KotshiJsonAdapterFactory
object EventsJsonAdapterFactory : JsonAdapter.Factory by KotshiEventsJsonAdapterFactory

fun AutoMappingConfiguration<Builder>.withEventsMapping() = apply {
    text({ BigDecimal(it) }, { it.toPlainString() })
}

