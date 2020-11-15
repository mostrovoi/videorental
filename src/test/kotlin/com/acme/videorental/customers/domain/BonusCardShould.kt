package com.acme.videorental.customers.domain

import com.acme.videorental.sharedKernel.domain.FilmTypeEnum
import org.assertj.core.api.Assertions
import org.junit.Test

class BonusCardShould {

    @Test
    fun be_created_correctly() {
        val bonusCard = BonusCard()
        Assertions.assertThat(bonusCard.bonusPoints).isEqualTo(0)
    }

    @Test
    fun update_bonus_points_correctly() {
        val bonusCard = BonusCard()
        val updatedBonusCardWithOldMovie = BonusCard.updateBonusPoints(bonusCard, FilmTypeEnum.OLD)
        Assertions.assertThat(updatedBonusCardWithOldMovie.bonusPoints).isEqualTo(1)
    }
}