using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class cont : MonoBehaviour
{
    public Button button;
    public GameObject panel;
    void Start()
    {
        Button btn = button.GetComponent<Button>();
        btn.onClick.AddListener(OnClick);
    }

    // Update is called once per frame
    void OnClick()
    {
        Time.timeScale = 1;
        panel.SetActive(false);
    }
}
