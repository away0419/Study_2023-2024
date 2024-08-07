package com.example.kotlin.repeat

import org.slf4j.LoggerFactory
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.item.ItemProcessor
import org.springframework.batch.item.ItemReader
import org.springframework.batch.item.ItemWriter
import org.springframework.batch.item.support.ListItemReader
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.batch.repeat.policy.SimpleCompletionPolicy
import org.springframework.batch.repeat.support.RepeatTemplate
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager
import java.util.*

@Configuration
class RepeatChunkJobConfiguration {
    private val log = LoggerFactory.getLogger(this.javaClass)!!


    @Bean
    fun repeatChunkItemReader(): ItemReader<String> = ListItemReader(listOf("one", "two", "three"))

    @Bean
    fun repeatChunkItemProcessor(): ItemProcessor<String, String> = ItemProcessor {
        val repeatTemplate = RepeatTemplate()
        repeatTemplate.setCompletionPolicy(SimpleCompletionPolicy(3))
        repeatTemplate.iterate{
            log.info("repeatChunkProcessor")
            RepeatStatus.CONTINUABLE
        }

        it.uppercase(Locale.getDefault()) }

    @Bean
    fun repeatChunkItemWriter(): ItemWriter<String> = ItemWriter { items -> items.forEach { log.info(it) } }

    @Bean
    fun repeatChunkStep(
        jobRepository: JobRepository,
        platformTransactionManager: PlatformTransactionManager
    ): Step {
        log.info(">>>> repeatChunkStep")
        return StepBuilder("repeatChunkStep", jobRepository)
            .chunk<String, String>(10, platformTransactionManager)
            .reader(repeatChunkItemReader())
            .processor(repeatChunkItemProcessor())
            .writer(repeatChunkItemWriter())
            .build()
    }

    @Bean
    fun repeatChunkJob(jobRepository: JobRepository, repeatChunkStep: Step): Job {
        log.info(">>>> repeatChunkJob")
        return JobBuilder("repeatChunkJob", jobRepository)
            .start(repeatChunkStep)
            .build()
    }

}