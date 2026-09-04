# Accessible UI foundation

Use this reference whenever the generated project contains a visual user interface.

## Objective

Every generated visual application must provide:

- A− for decreasing readable text;
- reset for restoring the default text size;
- A+ for increasing readable text;
- light theme;
- dark theme;
- automatic initial theme selection;
- persistence of accessibility preferences;
- keyboard-accessible controls;
- visible focus;
- appropriate contrast;
- tests and documentation.

Keep accessibility preferences separate from business rules.

Do not require backend persistence unless the briefing explicitly requires synchronization between devices.

## Required controls

Provide an accessibility control group containing:

- decrease text size;
- reset text size;
- increase text size;
- switch between light and dark themes.

Use semantic `button` elements.

Every control must have an accessible name through visible text, `aria-label`, or an equivalent mechanism supported by the selected platform.

Do not rely exclusively on:

- icons;
- colors;
- hover behavior;
- mouse input;
- visual position.

Recommended controls:

```html
<div
  class="accessibility-controls"
  role="group"
  aria-label="Controles de acessibilidade"
>
  <button
    type="button"
    aria-label="Diminuir tamanho do texto"
  >
    A−
  </button>

  <button
    type="button"
    aria-label="Restaurar tamanho padrão do texto"
  >
    A
  </button>

  <button
    type="button"
    aria-label="Aumentar tamanho do texto"
  >
    A+
  </button>

  <button
    type="button"
    aria-label="Ativar tema escuro"
    aria-pressed="false"
  >
    Tema
  </button>
</div>
```

Adapt this example to the selected framework.

## Text scaling

Implement application text scaling with a value applied at the document root.

Use the following default range:

| Level | Percentage | Scale |
|---|---:|---:|
| Minimum | 87.5% | 0.875 |
| Default | 100% | 1 |
| Intermediate | 112.5% | 1.125 |
| Maximum | 125% | 1.25 |

Use an increment of `0.125`.

The implementation must:

- prevent values below `0.875`;
- prevent values above `1.25`;
- disable A− at the minimum;
- disable A+ at the maximum;
- restore the default value through the reset control;
- persist the selected value;
- restore the preference when the application starts;
- validate persisted values;
- use relative units such as `rem` and `em` where appropriate;
- preserve navigation, forms, dialogs, messages, tables and actions;
- avoid clipping and overlapping;
- avoid horizontal scrolling caused only by the supported text scale.

Do not simulate text resizing with:

- browser zoom automation;
- `transform: scale(...)`;
- fixed enlargement of the entire page;
- duplicated styles for every component.

Recommended CSS:

```css
:root {
  --app-font-scale: 1;
  font-size: calc(100% * var(--app-font-scale));
}
```

Recommended JavaScript behavior:

```javascript
const MIN_FONT_SCALE = 0.875;
const DEFAULT_FONT_SCALE = 1;
const MAX_FONT_SCALE = 1.25;
const FONT_SCALE_STEP = 0.125;

function normalizeFontScale(value) {
  const parsedValue = Number(value);

  if (!Number.isFinite(parsedValue)) {
    return DEFAULT_FONT_SCALE;
  }

  return Math.min(
    MAX_FONT_SCALE,
    Math.max(MIN_FONT_SCALE, parsedValue)
  );
}

function applyFontScale(value) {
  const normalizedValue = normalizeFontScale(value);

  document.documentElement.style.setProperty(
    "--app-font-scale",
    String(normalizedValue)
  );

  return normalizedValue;
}
```

Adapt the behavior to a service, store, context, hook, composable, directive, controller, or equivalent abstraction.

## Theme foundation

Define theme colors as centralized semantic tokens.

Do not spread hardcoded light and dark colors throughout individual components.

At minimum, define tokens for:

- page background;
- surface background;
- elevated surface;
- primary text;
- secondary text;
- borders;
- primary action;
- secondary action;
- focus indicator;
- success;
- warning;
- error;
- disabled state.

Recommended CSS:

```css
:root,
[data-theme="light"] {
  color-scheme: light;

  --color-background: #ffffff;
  --color-surface: #f6f7f9;
  --color-surface-elevated: #ffffff;
  --color-text: #17202a;
  --color-text-secondary: #4b5563;
  --color-border: #c7cdd4;
  --color-primary: #185adb;
  --color-primary-text: #ffffff;
  --color-focus: #005fcc;
  --color-success: #166534;
  --color-warning: #854d0e;
  --color-error: #b91c1c;
  --color-disabled: #6b7280;
}

[data-theme="dark"] {
  color-scheme: dark;

  --color-background: #111827;
  --color-surface: #1f2937;
  --color-surface-elevated: #273449;
  --color-text: #f9fafb;
  --color-text-secondary: #d1d5db;
  --color-border: #64748b;
  --color-primary: #7db3ff;
  --color-primary-text: #08111f;
  --color-focus: #facc15;
  --color-success: #86efac;
  --color-warning: #fde047;
  --color-error: #fca5a5;
  --color-disabled: #9ca3af;
}
```

These values are starting examples.

Adapt the colors to the product identity while preserving the required contrast.

Components must consume semantic variables:

```css
body {
  margin: 0;
  color: var(--color-text);
  background: var(--color-background);
}

.card {
  color: var(--color-text);
  background: var(--color-surface);
  border: 1px solid var(--color-border);
}

.primary-button {
  color: var(--color-primary-text);
  background: var(--color-primary);
}
```

## Theme behavior

Support the following theme values:

- `light`;
- `dark`;
- `system`.

When no explicit preference exists:

1. inspect `prefers-color-scheme`;
2. use dark when the operating system prefers dark;
3. otherwise use light.

An explicit user selection must take precedence over the operating-system preference.

The implementation must:

- persist an explicit selection;
- restore it during application startup;
- validate the stored value;
- update the control state;
- update the accessible label;
- apply the theme to all screens;
- apply the theme to forms, dialogs, tables, notifications and loading states;
- use `color-scheme`;
- minimize flashes of the incorrect theme.

Recommended theme resolution:

```javascript
const SUPPORTED_THEMES = ["light", "dark", "system"];

function normalizeTheme(value) {
  return SUPPORTED_THEMES.includes(value)
    ? value
    : "system";
}

function resolveTheme(theme) {
  if (theme === "light" || theme === "dark") {
    return theme;
  }

  return window.matchMedia("(prefers-color-scheme: dark)").matches
    ? "dark"
    : "light";
}

function applyTheme(theme) {
  const preference = normalizeTheme(theme);
  const resolvedTheme = resolveTheme(preference);

  document.documentElement.dataset.theme = resolvedTheme;

  return {
    preference,
    resolvedTheme
  };
}
```

When the preference is `system`, react to operating-system theme changes if the selected platform supports it.

## Persistence

Use stable keys scoped to the application.

Recommended pattern:

```text
<application-name>.accessibility.theme
<application-name>.accessibility.font-scale
```

Example:

```text
barbershop.accessibility.theme
barbershop.accessibility.font-scale
```

Persist only accessibility preferences.

Do not store:

- authentication tokens;
- passwords;
- personal information;
- authorization state;
- confidential business information.

Validate every restored value.

When a persisted value is invalid, return safely to the default.

Handle unavailable or blocked client storage without preventing the application from starting.

## Architecture

Recommended responsibilities:

### Preference service or store

Responsible for:

- current theme preference;
- resolved theme;
- current font scale;
- minimum and maximum limits;
- preference validation;
- local persistence;
- system theme observation;
- applying preferences to the application root.

### Accessibility controls

Responsible for:

- presenting A−, reset and A+;
- presenting the theme control;
- accessible labels;
- disabled states;
- keyboard interaction;
- displaying the current state when appropriate.

### Theme tokens

Responsible for:

- light-theme values;
- dark-theme values;
- focus colors;
- feedback colors;
- consistent component styling.

### Application root

Responsible for:

- initializing preferences;
- applying the active theme;
- applying the active font scale.

Do not scatter direct local-storage access throughout multiple components.

## Framework adaptation

### Angular

Prefer:

- an injectable accessibility preference service;
- signals, observables, or state appropriate to the project version;
- a reusable accessibility-controls component;
- initialization at the application root;
- component and service tests.

### React

Prefer:

- an accessibility context or focused custom hook;
- a provider close to the application root;
- a reusable controls component;
- Testing Library tests based on user behavior.

### Vue

Prefer:

- an accessibility composable or store;
- initialization in the root application;
- a reusable controls component;
- component tests based on public behavior.

### Server-rendered frontend

Prefer:

- a reusable server-side fragment;
- a small client-side preference module;
- theme initialization as early as practical;
- no dependency on a backend request for every preference change.

### Vanilla HTML, CSS and JavaScript

Prefer:

- a focused accessibility module;
- CSS custom properties;
- semantic HTML buttons;
- DOMContentLoaded initialization;
- no global business-state coupling.

## Keyboard and focus

Every accessibility control must:

- be reachable with Tab;
- be operable with Enter;
- be operable with Space when represented by a button;
- have a visible focus indicator;
- follow a logical focus order;
- remain usable at all supported viewport sizes.

Do not add positive `tabindex` values.

Recommended focus style:

```css
:focus-visible {
  outline: 3px solid var(--color-focus);
  outline-offset: 3px;
}
```

Do not remove focus outlines unless an equally visible replacement exists.

## Contrast

Target WCAG 2.2 AA contrast:

- at least `4.5:1` for ordinary text;
- at least `3:1` for large text;
- at least `3:1` for meaningful interface components and graphical objects.

Verify both light and dark themes.

Do not communicate success, warning, error, selection, or status exclusively through color.

Combine color with at least one of:

- text;
- icon with accessible name;
- pattern;
- border;
- explicit status label.

## Reduced motion

Respect the operating-system reduced-motion preference.

Recommended CSS:

```css
@media (prefers-reduced-motion: reduce) {
  *,
  *::before,
  *::after {
    scroll-behavior: auto !important;
    animation-duration: 0.01ms !important;
    animation-iteration-count: 1 !important;
    transition-duration: 0.01ms !important;
  }
}
```

Do not remove information when motion is reduced.

## Responsive behavior

Test accessibility controls and the main journey at:

- a narrow mobile viewport;
- a tablet-sized viewport when supported;
- a desktop viewport;
- maximum text scale.

Controls may wrap onto another line.

Do not hide accessibility controls solely because the viewport is narrow.

At maximum text size, verify that:

- buttons remain readable;
- labels are not clipped;
- inputs remain associated with labels;
- dialogs remain usable;
- navigation remains reachable;
- tables have an appropriate overflow strategy;
- error messages remain visible;
- the primary action remains reachable.

## Required unit tests

Test:

- default font scale;
- increasing the font scale;
- decreasing the font scale;
- minimum limit;
- maximum limit;
- reset behavior;
- default system theme;
- explicit light theme;
- explicit dark theme;
- invalid persisted font value;
- invalid persisted theme value;
- restoration of persisted preferences;
- behavior when storage is unavailable;
- system theme fallback.

## Required component or integration tests

Test:

- A− updates the document root;
- reset restores the document root;
- A+ updates the document root;
- A− becomes disabled at the minimum;
- A+ becomes disabled at the maximum;
- the theme control updates the document root;
- controls expose accessible names;
- the theme control exposes its current state;
- preferences remain after recreating the service or reloading;
- keyboard activation works;
- representative forms use active theme tokens;
- representative dialogs use active theme tokens.

## Required system or end-to-end tests

Exercise the principal user journey in:

- light theme;
- dark theme;
- default text size;
- maximum text size;
- keyboard-only navigation.

Verify that no critical content or action becomes:

- hidden;
- clipped;
- overlapping;
- unreadable;
- unreachable.

If an automated accessibility scanner is available, run it.

Do not treat an automated scanner as a substitute for keyboard and visual verification.

## Documentation

The generated README must explain:

- where the accessibility controls are located;
- how to use A−;
- how to reset the text;
- how to use A+;
- supported text-size range;
- light-theme behavior;
- dark-theme behavior;
- system-preference behavior;
- where preferences are stored;
- how to clear or reset preferences;
- how to run accessibility-related tests;
- known limitations.

## Verification states

Classify every accessibility check as one of:

- `passed`;
- `failed`;
- `not run`;
- `not applicable`.

Do not mark a check as `passed` unless it was actually executed or directly verified.

Explain every `failed`, `not run`, and `not applicable` result.

## Acceptance criteria

Mark the accessibility foundation complete only when:

- A− works;
- reset works;
- A+ works;
- minimum and maximum limits work;
- font preference survives a reload;
- light theme works;
- dark theme works;
- theme preference survives a reload;
- system preference works when no explicit preference exists;
- controls have accessible names;
- controls are keyboard accessible;
- focus is visible;
- representative content meets the contrast target;
- critical information does not rely only on color;
- the primary journey remains usable at maximum text size;
- automated tests relevant to the selected stack pass;
- documentation explains the feature.

If any mandatory criterion fails, report the accessibility foundation as incomplete.

For projects without a visual interface, report:

```text
Accessibility UI foundation: not applicable
Reason: the generated project has no visual user interface.
```