using UnityEngine;
using UnityEngine.UI;

public class stop : MonoBehaviour
{
    public Button button;
    public GameObject panel;
    void Start()
    {
        Button btn = button.GetComponent<Button>();
        btn.onClick.AddListener(Onclick);
    }
    void Onclick()
    {
        Time.timeScale = 0;
        panel.gameObject.SetActive(true);
    }
}
