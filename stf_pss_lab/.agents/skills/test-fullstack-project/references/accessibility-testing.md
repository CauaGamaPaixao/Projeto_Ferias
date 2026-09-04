# Accessibility testing

For every visual interface, test the complete accessible UI foundation and representative content.

## Required behavior

- A− decreases text to no less than 87.5% and disables at minimum.
- Reset restores 100%.
- A+ increases text to no more than 125% and disables at maximum.
- Font scale persists and invalid stored values recover safely.
- Light, dark, and initial system preference work; explicit theme persists and system changes are handled when applicable.
- Controls have accessible names, logical order, keyboard activation, and visible focus.
- WCAG 2.2 AA contrast targets are checked in both themes; information is not color-only.
- Reduced-motion preference preserves information.
- At maximum scale and supported mobile/desktop widths, no critical content/action is hidden, clipped, overlapped, unreadable, or unreachable.

## Evidence layers

Use unit tests for preference logic, component tests for root/control state, E2E for reload and principal journey, automated scanners for detectable issues, and keyboard plus visual inspection for behavior scanners cannot prove. A scanner never substitutes for keyboard or visual verification.
