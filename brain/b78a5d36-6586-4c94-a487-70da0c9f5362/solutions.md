# Half-Yearly Examination 2026–27 — Class XII Mathematics
## Complete Solutions

---

## SECTION A — Multiple Choice Questions (1 mark each)

### Q1. Relation R = {(1,1),(2,2),(3,3),(1,2)} on A = {1,2,3}

- **Reflexive** ✓ — $(1,1), (2,2), (3,3) \in R$
- **Symmetric** ✗ — $(1,2) \in R$ but $(2,1) \notin R$
- **Transitive** ✓ — The only non-diagonal pair is $(1,2)$. We check: $(1,2) \in R$ and $(2,2) \in R \Rightarrow (1,2) \in R$ ✓. No chain violates transitivity.

> **Answer: (b) Reflexive and transitive only**

---

### Q2. $f : \mathbb{R} \to \mathbb{R}$, $f(x) = x^2$

- **Not one-one:** $f(1) = f(-1) = 1$, but $1 \neq -1$.
- **Not onto:** There is no $x \in \mathbb{R}$ such that $x^2 = -1$.

> **Answer: (d) Neither one-one nor onto**

---

### Q3. Principal value of $\sin^{-1}(-1/2)$

The principal value branch of $\sin^{-1}$ is $[-\pi/2,\;\pi/2]$.

$$\sin\!\left(-\frac{\pi}{6}\right) = -\frac{1}{2}$$

> **Answer: (b) $-\pi/6$**

---

### Q4. Domain of $\cos^{-1}(2x - 1)$

We need $-1 \le 2x - 1 \le 1$, i.e. $0 \le 2x \le 2$, i.e. $0 \le x \le 1$.

> **Answer: (a) [0, 1]**

---

### Q5. $|3A|$ where $A$ is $3 \times 3$ and $|A| = 5$

For an $n \times n$ matrix, $|kA| = k^n |A|$. Here $n = 3$:

$$|3A| = 3^3 \times 5 = 27 \times 5 = 135$$

> **Answer: (c) 135**

---

### Q6. $(A + A^\top)$ is always …

$$(A + A^\top)^\top = A^\top + (A^\top)^\top = A^\top + A = A + A^\top$$

Since it equals its own transpose, it is symmetric.

> **Answer: (b) Symmetric**

---

### Q7. Value of $x$ for skew-symmetry

$$A = \begin{pmatrix} 0 & 1 & -2 \\ -1 & 0 & x \\ 2 & -3 & 0 \end{pmatrix}$$

For skew-symmetry: $a_{ij} = -a_{ji}$. We need $a_{23} = -a_{32}$:

$$x = -(-3) = 3$$

> **Answer: (a) 3**

---

### Q8. $|\text{adj}\,A|$ where $A$ is $3 \times 3$ with $|A| = 4$

$$|\text{adj}\,A| = |A|^{n-1} = 4^{3-1} = 4^2 = 16$$

> **Answer: (c) 16**

---

### Q9. Area of triangle with vertices $(0,0)$, $(4,0)$, $(0,5)$

$$\text{Area} = \frac{1}{2}\left|x_1(y_2 - y_3) + x_2(y_3 - y_1) + x_3(y_1 - y_2)\right|$$
$$= \frac{1}{2}\left|0(0-5) + 4(5-0) + 0(0-0)\right| = \frac{1}{2}|20| = 10$$

> **Answer: (b) 10 sq units**

---

### Q10. $A = \begin{pmatrix} 2 & 3 \\ 4 & 6 \end{pmatrix}$ is singular because …

$$|A| = 2(6) - 3(4) = 12 - 12 = 0$$

> **Answer: (a) $|A| = 0$**

---

### Q11. $f(x) = |x|$

$f(x) = |x|$ is continuous everywhere. At $x = 0$, the left-hand derivative is $-1$ and the right-hand derivative is $+1$, so it is not differentiable at $x = 0$.

> **Answer: (b) Continuous everywhere but not differentiable at $x = 0$**

---

### Q12. $y = e^{2x}$, find $dy/dx$

$$\frac{dy}{dx} = 2e^{2x}$$

> **Answer: (b) $2e^{2x}$**

---

### Q13. Continuity of piecewise function at $x = 5$

$$f(x) = \begin{cases} kx + 1 & x \le 5 \\ 3x - 5 & x > 5 \end{cases}$$

For continuity: $\lim_{x \to 5^-} f(x) = \lim_{x \to 5^+} f(x)$

$$5k + 1 = 3(5) - 5 = 10 \implies k = \frac{9}{5}$$

> **Answer: (a) $9/5$**

---

### Q14. Rate of change of area of circle at $r = 5$

$$A = \pi r^2 \implies \frac{dA}{dr} = 2\pi r$$

At $r = 5$: $\frac{dA}{dr} = 10\pi$

> **Answer: (b) $10\pi$ cm²/cm**

---

### Q15. $f(x) = x^2 - 4x + 6$ is strictly increasing in …

$$f'(x) = 2x - 4 > 0 \implies x > 2$$

> **Answer: (b) $(2, \infty)$**

---

### Q16. Point on $y = x^2$ where tangent is parallel to $x$-axis

$$\frac{dy}{dx} = 2x = 0 \implies x = 0,\quad y = 0$$

> **Answer: (b) $(0, 0)$**

---

### Q17. $\int \sec^2 x\, dx$

> **Answer: (a) $\tan x + C$**

---

### Q18. $\int \frac{1}{1 + x^2}\, dx$

> **Answer: (a) $\tan^{-1} x + C$**

---

### Q19. Assertion–Reason: $f(x) = |x - 2|$

- **Assertion (A):** $f(x) = |x-2|$ is continuous at $x = 2$ — **TRUE** (absolute value functions are continuous everywhere).
- **Reason (R):** $f(x) = |x-2|$ is differentiable at $x = 2$ — **FALSE** (there is a corner/cusp at $x = 2$).

> **Answer: (c) A is true, but R is false**

---

### Q20. Assertion–Reason: $f(x) = -(x-3)^2 + 4$

- **Assertion (A):** $(3, 4)$ is a point of local maximum — **TRUE**.
  - $f'(x) = -2(x - 3)$, so $f'(3) = 0$.
  - $f''(x) = -2 < 0$, confirming local maximum.
- **Reason (R):** At a local maximum, $f'(x) = 0$ and $f''(x) < 0$ — **TRUE**, and this is exactly how we verified the assertion.

> **Answer: (a) Both A and R are true, and R is the correct explanation of A**

---

## SECTION B — Short Answer Questions (2 marks each)

### Q21. Check Reflexive, Symmetric, Transitive for $R = \{(1,1),(2,2),(3,3),(1,2),(2,3)\}$ on $A = \{1,2,3\}$

| Property | Check | Result |
|---|---|---|
| **Reflexive** | $(1,1), (2,2), (3,3) \in R$ | ✅ Yes |
| **Symmetric** | $(1,2) \in R$ but $(2,1) \notin R$ | ❌ No |
| **Transitive** | $(1,2) \in R$ and $(2,3) \in R$ but $(1,3) \notin R$ | ❌ No |

**Conclusion:** $R$ is reflexive, but neither symmetric nor transitive.

---

### Q22. Find $\tan^{-1}(1) + \cos^{-1}(-1/2)$

$$\tan^{-1}(1) = \frac{\pi}{4}$$

$$\cos^{-1}\!\left(-\frac{1}{2}\right) = \frac{2\pi}{3}$$

$$\therefore \tan^{-1}(1) + \cos^{-1}\!\left(-\frac{1}{2}\right) = \frac{\pi}{4} + \frac{2\pi}{3} = \frac{3\pi + 8\pi}{12} = \boxed{\frac{11\pi}{12}}$$

---

### Q23. Find $2A - 3B$

$$2A - 3B = 2\begin{pmatrix}1&2\\3&4\end{pmatrix} - 3\begin{pmatrix}2&0\\1&3\end{pmatrix} = \begin{pmatrix}2&4\\6&8\end{pmatrix} - \begin{pmatrix}6&0\\3&9\end{pmatrix} = \boxed{\begin{pmatrix}-4&4\\3&-1\end{pmatrix}}$$

---

### Q24. Differentiate $y = \sin(x^2 + 1)$

Using the chain rule:

$$\frac{dy}{dx} = \cos(x^2 + 1) \cdot \frac{d}{dx}(x^2 + 1) = \boxed{2x\cos(x^2 + 1)}$$

---

### Q25. Evaluate $\int x\, e^{x^2}\, dx$

Let $u = x^2 \implies du = 2x\, dx \implies x\, dx = \frac{du}{2}$

$$\int x\, e^{x^2}\, dx = \frac{1}{2}\int e^u\, du = \frac{1}{2}e^u + C = \boxed{\frac{1}{2}e^{x^2} + C}$$

---

## SECTION C — Short Answer Questions (3 marks each)

### Q26. Show that $f(x) = 3x + 2$ is one-one and onto ($f : \mathbb{R} \to \mathbb{R}$)

**One-one:** Let $f(x_1) = f(x_2)$.

$$3x_1 + 2 = 3x_2 + 2 \implies x_1 = x_2$$

Hence $f$ is one-one. ✓

**Onto:** Let $y \in \mathbb{R}$ (codomain). We need $x \in \mathbb{R}$ such that $f(x) = y$.

$$3x + 2 = y \implies x = \frac{y - 2}{3} \in \mathbb{R}$$

Since for every $y \in \mathbb{R}$ there exists a pre-image, $f$ is onto. ✓

---

### Q27. Prove that $\tan^{-1}(1/2) + \tan^{-1}(1/3) = \pi/4$

Using the identity $\tan^{-1} a + \tan^{-1} b = \tan^{-1}\!\left(\frac{a+b}{1-ab}\right)$ when $ab < 1$:

Here $ab = \frac{1}{2} \cdot \frac{1}{3} = \frac{1}{6} < 1$, so the identity applies.

$$\tan^{-1}\!\left(\frac{\frac{1}{2}+\frac{1}{3}}{1-\frac{1}{6}}\right) = \tan^{-1}\!\left(\frac{\frac{5}{6}}{\frac{5}{6}}\right) = \tan^{-1}(1) = \frac{\pi}{4}$$

Hence proved. $\blacksquare$

---

### Q28. Find $A^{-1}$ using the adjoint method and verify $AA^{-1} = I$

$$A = \begin{pmatrix}2&1\\1&1\end{pmatrix}$$

**Step 1:** $|A| = 2(1) - 1(1) = 1 \neq 0$, so $A^{-1}$ exists.

**Step 2:** Cofactor matrix:
$C_{11} = 1,\; C_{12} = -1,\; C_{21} = -1,\; C_{22} = 2$

**Step 3:** $\text{adj}(A) = \begin{pmatrix}1&-1\\-1&2\end{pmatrix}$

**Step 4:** $A^{-1} = \frac{1}{|A|}\,\text{adj}(A) = \begin{pmatrix}1&-1\\-1&2\end{pmatrix}$

**Verification:**

$$AA^{-1} = \begin{pmatrix}2&1\\1&1\end{pmatrix}\begin{pmatrix}1&-1\\-1&2\end{pmatrix} = \begin{pmatrix}2-1&-2+2\\1-1&-1+2\end{pmatrix} = \begin{pmatrix}1&0\\0&1\end{pmatrix} = I \quad\checkmark$$

---

### Q29. Evaluate the determinant using minors and cofactors

$$\begin{vmatrix}1&2&3\\0&1&4\\5&6&0\end{vmatrix}$$

Expanding along $R_1$:

| Element | Minor | Cofactor | Contribution |
|---|---|---|---|
| $a_{11} = 1$ | $M_{11} = (1)(0)-(4)(6) = -24$ | $C_{11} = +(-24) = -24$ | $1 \times (-24) = -24$ |
| $a_{12} = 2$ | $M_{12} = (0)(0)-(4)(5) = -20$ | $C_{12} = -(-20) = 20$ | $2 \times 20 = 40$ |
| $a_{13} = 3$ | $M_{13} = (0)(6)-(1)(5) = -5$ | $C_{13} = +(-5) = -5$ | $3 \times (-5) = -15$ |

$$|A| = -24 + 40 - 15 = \boxed{1}$$

---

### Q30. If $y = x^x$ ($x > 0$), find $dy/dx$

Taking natural logarithm on both sides:

$$\ln y = x \ln x$$

Differentiating both sides with respect to $x$:

$$\frac{1}{y}\,\frac{dy}{dx} = x \cdot \frac{1}{x} + \ln x \cdot 1 = 1 + \ln x$$

$$\boxed{\frac{dy}{dx} = x^x(1 + \ln x)}$$

---

### Q31. Intervals of increase/decrease for $f(x) = 2x^3 - 15x^2 + 36x + 1$

$$f'(x) = 6x^2 - 30x + 36 = 6(x^2 - 5x + 6) = 6(x-2)(x-3)$$

| Interval | Sign of $(x-2)$ | Sign of $(x-3)$ | Sign of $f'(x)$ | Nature |
|---|---|---|---|---|
| $(-\infty, 2)$ | $-$ | $-$ | $+$ | Increasing ↑ |
| $(2, 3)$ | $+$ | $-$ | $-$ | Decreasing ↓ |
| $(3, \infty)$ | $+$ | $+$ | $+$ | Increasing ↑ |

**(i)** Strictly increasing on $(-\infty, 2) \cup (3, \infty)$

**(ii)** Strictly decreasing on $(2, 3)$

---

## SECTION D — Long Answer Questions (5 marks each)

### Q32. Solve using matrix method: $x + y + z = 6$, $y + 3z = 11$, $x - 2y + z = 0$

**Matrix form:** $AX = B$

$$A = \begin{pmatrix}1&1&1\\0&1&3\\1&-2&1\end{pmatrix},\quad X = \begin{pmatrix}x\\y\\z\end{pmatrix},\quad B = \begin{pmatrix}6\\11\\0\end{pmatrix}$$

**Step 1:** Compute $|A|$

$$|A| = 1(1+6) - 1(0-3) + 1(0-1) = 7 + 3 - 1 = 9 \neq 0$$

**Step 2:** Cofactors of $A$

$$\begin{aligned}
C_{11} &= +(1-(-6)) = 7 & C_{12} &= -(0-3) = 3 & C_{13} &= +(0-1) = -1 \\
C_{21} &= -(1+2) = -3 & C_{22} &= +(1-1) = 0 & C_{23} &= -(-2-1) = 3 \\
C_{31} &= +(3-1) = 2 & C_{32} &= -(3-0) = -3 & C_{33} &= +(1-0) = 1
\end{aligned}$$

**Step 3:** $\text{adj}(A) = \begin{pmatrix}7&-3&2\\3&0&-3\\-1&3&1\end{pmatrix}$

**Step 4:** $A^{-1} = \frac{1}{9}\begin{pmatrix}7&-3&2\\3&0&-3\\-1&3&1\end{pmatrix}$

**Step 5:** $X = A^{-1}B$

$$X = \frac{1}{9}\begin{pmatrix}7&-3&2\\3&0&-3\\-1&3&1\end{pmatrix}\begin{pmatrix}6\\11\\0\end{pmatrix} = \frac{1}{9}\begin{pmatrix}42-33+0\\18+0+0\\-6+33+0\end{pmatrix} = \frac{1}{9}\begin{pmatrix}9\\18\\27\end{pmatrix} = \begin{pmatrix}1\\2\\3\end{pmatrix}$$

$$\boxed{x = 1,\quad y = 2,\quad z = 3}$$

---

### Q33. If $y = (\sin^{-1}x)^2$, prove that $(1-x^2)\,\frac{d^2y}{dx^2} - x\,\frac{dy}{dx} - 2 = 0$

**Step 1:** Differentiate $y = (\sin^{-1}x)^2$:

$$\frac{dy}{dx} = 2\sin^{-1}x \cdot \frac{1}{\sqrt{1-x^2}}$$

$$\Longrightarrow \sqrt{1-x^2}\;\frac{dy}{dx} = 2\sin^{-1}x \tag{*}$$

**Step 2:** Differentiate $(*)$ with respect to $x$:

$$\sqrt{1-x^2}\;\frac{d^2y}{dx^2} + \frac{dy}{dx}\cdot\frac{-x}{\sqrt{1-x^2}} = \frac{2}{\sqrt{1-x^2}}$$

**Step 3:** Multiply throughout by $\sqrt{1-x^2}$:

$$(1-x^2)\,\frac{d^2y}{dx^2} - x\,\frac{dy}{dx} = 2$$

$$(1-x^2)\,\frac{d^2y}{dx^2} - x\,\frac{dy}{dx} - 2 = 0 \quad\blacksquare$$

---

### Q34. Wire of 28 m — minimize combined area of square and circle

Let the piece bent into a **square** have length $4a$ (side $= a$) and the piece bent into a **circle** have length $2\pi r$.

$$4a + 2\pi r = 28 \implies a = \frac{28 - 2\pi r}{4} = \frac{14 - \pi r}{2}$$

**Total area:**

$$A = a^2 + \pi r^2 = \left(\frac{14 - \pi r}{2}\right)^2 + \pi r^2$$

$$A = \frac{(14-\pi r)^2}{4} + \pi r^2$$

**Differentiate and set to zero:**

$$\frac{dA}{dr} = \frac{2(14-\pi r)(-\pi)}{4} + 2\pi r = \frac{-\pi(14-\pi r)}{2} + 2\pi r = 0$$

$$-\pi(14-\pi r) + 4\pi r = 0$$

$$-14\pi + \pi^2 r + 4\pi r = 0$$

$$r(\pi^2 + 4\pi) = 14\pi \implies r = \frac{14\pi}{\pi(\pi+4)} = \frac{14}{\pi+4}$$

$$a = \frac{14 - \pi \cdot \frac{14}{\pi+4}}{2} = \frac{14\!\left(\frac{\pi+4-\pi}{\pi+4}\right)}{2} = \frac{14 \cdot 4}{2(\pi+4)} = \frac{28}{\pi+4}$$

**Second derivative test:**

$$\frac{d^2A}{dr^2} = \frac{\pi^2 + 4\pi}{2} > 0 \quad\Longrightarrow \text{minimum confirmed}$$

**Lengths of the two pieces:**

$$\boxed{\text{Square:}\; 4a = \frac{112}{\pi+4} \;\text{m}} \qquad \boxed{\text{Circle:}\; 2\pi r = \frac{28\pi}{\pi+4} \;\text{m}}$$

**Verification:** $\frac{112}{\pi+4} + \frac{28\pi}{\pi+4} = \frac{112+28\pi}{\pi+4} = \frac{28(\pi+4)}{\pi+4} = 28$ ✓

---

### Q35. Evaluate $\int \frac{x^2+1}{x^2-5x+6}\,dx$

**Step 1:** Since the degree of numerator equals the degree of denominator, perform polynomial long division:

$$\frac{x^2+1}{x^2-5x+6} = 1 + \frac{5x-5}{x^2-5x+6} = 1 + \frac{5x-5}{(x-2)(x-3)}$$

**Step 2:** Partial fractions for $\frac{5x-5}{(x-2)(x-3)}$:

$$\frac{5x-5}{(x-2)(x-3)} = \frac{A}{x-2} + \frac{B}{x-3}$$

$$5x - 5 = A(x-3) + B(x-2)$$

- Put $x = 2$: $5 = A(-1) \implies A = -5$
- Put $x = 3$: $10 = B(1) \implies B = 10$

**Step 3:** Integrate:

$$\int\left[1 - \frac{5}{x-2} + \frac{10}{x-3}\right]dx$$

$$= \boxed{x - 5\ln|x-2| + 10\ln|x-3| + C}$$

---

## SECTION E — Case-Based Questions (4 marks each)

### Q36. Case Study — Relations and Functions

**Set** $A = \{1,2,3,4,5\}$, **Relation** $R = \{(a,b) : |a-b| \text{ is even}\}$

#### (i) Show that $R$ is an equivalence relation (2 marks)

**Reflexive:** For any $a \in A$, $|a - a| = 0$, which is even. So $(a,a) \in R$. ✓

**Symmetric:** If $(a,b) \in R$, then $|a-b|$ is even. Since $|b-a| = |a-b|$, we have $(b,a) \in R$. ✓

**Transitive:** If $(a,b) \in R$ and $(b,c) \in R$, then $|a-b|$ and $|b-c|$ are both even. This means $a - b$ is even and $b - c$ is even, so:

$$(a-b) + (b-c) = a - c \text{ is even} \implies |a-c| \text{ is even} \implies (a,c) \in R \quad\checkmark$$

Since $R$ is reflexive, symmetric, and transitive, **$R$ is an equivalence relation**. $\blacksquare$

#### (ii) Equivalence class of 2 (1 mark)

$$[2] = \{b \in A : |2 - b| \text{ is even}\}$$

Check each element: $|2-2|=0$ ✓, $|2-4|=2$ ✓. All others give odd differences.

$$\boxed{[2] = \{2, 4\}}$$

#### (iii) Is $(1, 5) \in R$? (1 mark)

$$|1 - 5| = 4, \text{ which is even.}$$

$$\boxed{\text{Yes, } (1,5) \in R.}$$

---

### Q37. Case Study — Application of Derivatives

$$C(x) = 0.005x^3 - 0.02x^2 + 30x + 5000$$

#### (i) Marginal cost function (2 marks)

$$MC(x) = \frac{dC}{dx} = 0.005(3x^2) - 0.02(2x) + 30$$

$$\boxed{MC(x) = 0.015x^2 - 0.04x + 30}$$

#### (ii) Marginal cost at $x = 3$ (1 mark)

$$MC(3) = 0.015(9) - 0.04(3) + 30 = 0.135 - 0.12 + 30 = \boxed{₹\,30.015}$$

#### (iii) Is cost increasing or decreasing at $x = 3$? (1 mark)

Since $MC(3) = 30.015 > 0$, the cost function $C(x)$ is **increasing** at $x = 3$.

This is because the marginal cost represents the rate of change of cost. A positive marginal cost means the cost increases when production increases.

---

### Q38. Case Study — Integrals

$$v(t) = 3t^2 - 12t + 9 \;\text{m/s}, \quad s(0) = 0$$

#### (i) Displacement function $s(t)$ (2 marks)

$$s(t) = \int v(t)\,dt = \int (3t^2 - 12t + 9)\,dt = t^3 - 6t^2 + 9t + C$$

Using $s(0) = 0$: $0 = 0 - 0 + 0 + C \implies C = 0$

$$\boxed{s(t) = t^3 - 6t^2 + 9t}$$

#### (ii) Displacement at $t = 2$ s (1 mark)

$$s(2) = 8 - 24 + 18 = \boxed{2 \text{ m}}$$

#### (iii) Values of $t \in [0, 3]$ when particle is momentarily at rest (1 mark)

The particle is at rest when $v(t) = 0$:

$$3t^2 - 12t + 9 = 0 \implies t^2 - 4t + 3 = 0 \implies (t-1)(t-3) = 0$$

$$\boxed{t = 1 \text{ s} \quad\text{and}\quad t = 3 \text{ s}}$$

---

> **— End of Solutions —**
