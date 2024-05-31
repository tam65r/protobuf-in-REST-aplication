from scipy.stats import wilcoxon


def hyphtesis_test(data,h0,h1, alternative = 'two-sided', alpha = 0.5):
    min_length = min(len(data[0]), len(data[1]))

    pGP_trimmed = data[0][:min_length]
    jGP_trimmed = data[1][:min_length]
    
    statistic, p_value = wilcoxon(pGP_trimmed, jGP_trimmed, alternative=alternative)
    
    if p_value < alpha:
        print(f"\np_value - {p_value} < {alpha} - H0 is rejected, therefore '{h1}'.")
    else:
        print(f"\np_value - {p_value} > {alpha} - H0 is not rejected, in other words '{h0}'.")

    return p_value