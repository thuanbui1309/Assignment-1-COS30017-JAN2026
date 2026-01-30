# Test Cases Documentation

## ScoringLogicTest (20 Tests)
Tests the core scoring mechanics and game rules.

| ID | Test Name | Component | Purpose |
|----|-----------|-----------|---------|
| 1 | `testInitialState_scoreIsZero` | Score Display | Verify initial score is 0 |
| 2 | `testInitialState_elementIsZeroOfTen` | Element Counter | Verify initial element is 0/10 |
| 3 | `testPerform_firstElement_addsOnePoint` | Perform Button | Verify first element adds 1 point |
| 4 | `testPerform_basicZone_addsOnePointEach` | Perform Button | Verify elements 1-3 add 1 point each |
| 5 | `testPerform_intermediateZone_addsTwoPointsEach` | Perform Button | Verify elements 4-7 add 2 points each |
| 6 | `testPerform_advancedZone_addsThreePointsEach` | Perform Button | Verify elements 8-10 add 3 points each |
| 7 | `testPerform_completeRoutine_maxScoreIs20` | Perform Button | Verify max score is 20 after full routine |
| 8 | `testPerform_afterRoutineComplete_doesNothing` | Perform Button | Verify no actions allowed after completion |
| 9 | `testDeduction_beforeFirstElement_doesNothing` | Deduct Button | Verify deduction button is disabled before starting |
| 10 | `testDeduction_afterFirstElement_subtractsTwoPoints` | Deduct Button | Verify deduction subtracts 2 points |
| 11 | `testDeduction_afterMultipleElements_subtractsTwoPoints` | Deduct Button | Verify deduction works from higher score |
| 12 | `testDeduction_endsRoutine_cannotContinuePerforming` | Deduct Button | Verify deduction terminates the routine |
| 13 | `testDeduction_cannotDeductTwice` | Deduct Button | Verify multiple deductions are not allowed |
| 14 | `testDeduction_afterRoutineComplete_doesNothing` | Deduct Button | Verify deduction impossible after completion |
| 15 | `testReset_afterPerforming_returnsToInitialState` | Reset Button | Verify reset clears score and element count |
| 16 | `testReset_afterDeduction_returnsToInitialState` | Reset Button | Verify reset works after deduction |
| 17 | `testReset_afterCompleteRoutine_returnsToInitialState` | Reset Button | Verify reset works after completion |
| 18 | `testReset_allowsNewRoutine` | Reset Button | Verify new routine can start after reset |
| 19 | `testScore_neverGoesNegative` | Score Limits | Verify score minimum limit (0) |
| 20 | `testScore_neverExceeds20` | Score Limits | Verify score maximum limit (20) |

---

## RequirementsTest (15 Tests)
Tests technical requirements: rotation, dialogs, button states, and UI elements.

| ID | Test Name | Component | Purpose |
|----|-----------|-----------|---------|
| 1 | `testRotation_preservesScore` | State Persistence | Verify score persists after rotation |
| 2 | `testRotation_preservesElement` | State Persistence | Verify element count persists after rotation |
| 3 | `testRotation_preservesDialogState` | State Persistence | Verify dialog remains visible after rotation |
| 4 | `testDialog_showsOnRoutineComplete` | Completion Dialog | Verify dialog appears when routine is complete |
| 5 | `testDialog_showsOnDeduction` | Completion Dialog | Verify dialog appears when deduction occurs |
| 6 | `testDialog_successShowsCorrectTitle` | Completion Dialog | Verify success dialog shows correct localized title |
| 7 | `testDialog_failureShowsCorrectTitle` | Completion Dialog | Verify failure dialog shows correct localized title |
| 8 | `testButtons_deductDisabledInitially` | Button States | Verify Deduct button is initially disabled |
| 9 | `testButtons_resetDisabledInitially` | Button States | Verify Reset button is initially disabled |
| 10 | `testButtons_enableAfterPerform` | Button States | Verify buttons enable after first action |
| 11 | `testButtons_disableAfterReset` | Button States | Verify buttons disable again after reset |
| 12 | `testUI_titleDisplayed` | UI Elements | Verify App Title is displayed correctly |
| 13 | `testUI_scoreDisplayed` | UI Elements | Verify Score is displayed |
| 14 | `testUI_elementDisplayed` | UI Elements | Verify Element count is displayed |
| 15 | `testUI_allButtonsDisplayed` | UI Elements | Verify all buttons are displayed |
