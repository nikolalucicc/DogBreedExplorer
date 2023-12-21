package com.dogbreedexplorer.utils.model

import com.dogbreedexplorer.ui.model.Breed

class BreedWithVotes (
    val breed: Breed,
    val votes: List<Vote>,
    val isVoted: Boolean
)