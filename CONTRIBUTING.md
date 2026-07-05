# Contributing

Thanks for wanting to contribute! This project is small but I care about keeping it clean.

## Before opening a PR

1. Open an issue or discussion first to describe the feature/bug.
2. Wait for a quick go-ahead so we don't duplicate effort.

## Code style

- This project follows **MVI architecture**.
- State should live in the **ViewModel/Store** as immutable state.
- Avoid `var` state inside Composables or business logic.
- Use actions/events to trigger state changes.

## PR requirements

- Keep PRs focused on one feature or fix.
- Make sure the feature is complete and useful in its current form.
- UI changes should be clear to the user (labels, icons, tooltips, etc.).
- Test the change locally before submitting.

## Reviews

All PRs will be reviewed and tested locally before merging. I may request changes — this is normal and helps keep the project consistent.
