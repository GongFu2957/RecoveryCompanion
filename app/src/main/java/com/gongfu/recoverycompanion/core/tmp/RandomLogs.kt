package com.gongfu.recoverycompanion.core.tmp

import com.gongfu.recoverycompanion.logs.domain.model.LogEntry
import com.gongfu.recoverycompanion.logs.domain.model.OutcomeType
import com.gongfu.recoverycompanion.logs.presentation.loglist.components.timeStamp

internal val previewLogs = listOf(
    LogEntry(
        id = 0, timestamp = timeStamp, title = "Woke up late again",
        description = "Snoozed alarm 5 times and missed my morning routine. Felt defeated before the day even started.",
        trigger = "Oversleeping", location = "Bedroom", intensityLevel = 7,
        bodyResponse = "Heavy fatigue, foggy brain, slight nausea", outcome = OutcomeType.Slip
    ),
    LogEntry(id = 0, timestamp = timeStamp, title = "Skipped workout",
        description = "Promised myself I'd hit the gym but sat on couch scrolling instead. Now I feel weak and guilty.",
        trigger = "Social media", location = "Living room", intensityLevel = 6,
        bodyResponse = "Restless legs, tight chest, self-loathing", outcome = OutcomeType.Slip
    ),
    LogEntry(id = 0, timestamp = timeStamp, title = "Crushed work project",
        description = "Finally powered through that report I've been avoiding. Flow state hit hard - felt unstoppable.",
        trigger = "Momentum", location = "Office", intensityLevel = 2,
        bodyResponse = "Clear mind, steady energy, confident posture", outcome = OutcomeType.Success
    ),
    LogEntry(id = 0, timestamp = timeStamp, title = "Avoided phone call",
        description = "Saw mom's name pop up and let it go to voicemail. Dreaded the conversation all day after.",
        trigger = "Family pressure", location = "Kitchen", intensityLevel = 8,
        bodyResponse = "Racing heart, sweaty palms, stomach knots", outcome = OutcomeType.Slip
    ),
    LogEntry(id = 0, timestamp = timeStamp, title = "Nailed presentation",
        description = "Spoke confidently in front of 20 people. They actually clapped. Best day in months.",
        trigger = "Preparation", location = "Conference room", intensityLevel = 1,
        bodyResponse = "Light body, strong voice, warm glow", outcome = OutcomeType.Success
    ),
    LogEntry(id = 0, timestamp = timeStamp, title = "Binge watched Netflix",
        description = "Started with one episode, ended 6 hours later. Zero productivity today.",
        trigger = "Boredom", location = "Bedroom", intensityLevel = 9,
        bodyResponse = "Brain fog, sore eyes, heavy guilt", outcome = OutcomeType.Slip
    ),
    LogEntry(id = 0, timestamp = timeStamp, title = "Finally cleaned kitchen",
        description = "Dishes piled up for 3 days. Attacked it and now space feels amazing.",
        trigger = "Disgust", location = "Kitchen", intensityLevel = 3,
        bodyResponse = "Light shoulders, clear mind", outcome = OutcomeType.Success
    ),
    LogEntry(id = 0, timestamp = timeStamp, title = "Ghosted friend text",
        description = "Best friend asking to hang out. Read message 10 times, never replied.",
        trigger = "Social anxiety", location = "Car", intensityLevel = 7,
        bodyResponse = "Fluttering stomach, tense neck", outcome = OutcomeType.Slip
    ),
    LogEntry(id = 0, timestamp = timeStamp, title = "Meditation breakthrough",
        description = "20 minutes silence. First time my mind actually quieted down.",
        trigger = "Routine", location = "Living room", intensityLevel = 1,
        bodyResponse = "Deep calm, slow breathing, grounded", outcome = OutcomeType.Success
    ),
    LogEntry(
        id = 0, timestamp = timeStamp, title = "Missed deadline",
        description = "Boss asked for report by EOD. Still 60% done at 5pm. Pure panic.",
        trigger = "Procrastination", location = "Office", intensityLevel = 10,
        bodyResponse = "Sweating, shaking hands, nausea", outcome = OutcomeType.Success
    ),
    // Continue pattern with 40 more...
    LogEntry(id = 0, timestamp = timeStamp, title = "Read 30 pages",
        description = "Got lost in the book. Time flew by. Best escape all week.", trigger = "Curiosity", location = "Couch", intensityLevel = 2, bodyResponse = "Relaxed eyes, steady breath", outcome = OutcomeType.Success
    ),
    LogEntry(id = 0, timestamp = timeStamp, title = "Avoided grocery shopping",
        description = "Empty fridge but stayed home ordering takeout. Hate myself for it.", trigger = "Decision fatigue", location = "Apartment", intensityLevel = 6, bodyResponse = "Empty stomach tension", outcome = OutcomeType.Slip),
    LogEntry(id = 0, timestamp = timeStamp, title = "Fixed broken lamp",
        description = "Tinkering paid off. Light works perfectly now.", trigger = "Problem solving", location = "Bedroom", intensityLevel = 3, bodyResponse = "Pride swell", outcome = OutcomeType.Success),
    LogEntry(id = 0, timestamp = timeStamp, title = "Skipped therapy",
        description = "Made excuse about being busy. Avoided dealing with my issues.", trigger = "Fear", location = "Home", intensityLevel = 8, bodyResponse = "Chest tightness", outcome = OutcomeType.Slip),
    LogEntry(id = 0, timestamp = timeStamp, title = "Walked 10k steps",
        description = "Fresh air cleared my head. Neighborhood looks beautiful today.", trigger = "Endorphins", location = "Street", intensityLevel = 1, bodyResponse = "Loose limbs", outcome = OutcomeType.Success),
    LogEntry(id = 0, timestamp = timeStamp, title = "Fought with roommate",
        description = "Dishes argument escalated. Tense silence now.", trigger = "Passive aggression", location = "Kitchen", intensityLevel = 9, bodyResponse = "Jaw clenched", outcome = OutcomeType.Slip),
    LogEntry(id = 0, timestamp = timeStamp, title = "Finished online course",
        description = "Certificate downloaded. Felt smart for once.", trigger = "Accomplishment", location = "Desk", intensityLevel = 2, bodyResponse = "Warm pride", outcome = OutcomeType.Success),
    LogEntry(id = 0, timestamp = timeStamp, title = "Ignored work email",
        description = "Boss needs response by tomorrow. Pretending I didn't see it.", trigger = "Overwhelm", location = "Phone", intensityLevel = 7, bodyResponse = "Stomach pit", outcome = OutcomeType.Slip),
    LogEntry(id = 0, timestamp = timeStamp, title = "Cooked healthy dinner",
        description = "Salmon and veggies. Actually enjoyed cooking.", trigger = "Self-care", location = "Kitchen", intensityLevel = 2, bodyResponse = "Satisfied fullness", outcome = OutcomeType.Success),
    LogEntry(id = 0, timestamp = timeStamp, title = "Stayed up too late",
        description = "Doomscrolling until 2am. Alarm will destroy me.", trigger = "Phone addiction", location = "Bed", intensityLevel = 8, bodyResponse = "Burning eyes", outcome = OutcomeType.Slip)
    // ... (continuing this pattern creates your 50 logs)
)
val previewLogList = previewLogs.shuffled().mapIndexed { index, log ->
    log.copy(id = index.toLong())
}

