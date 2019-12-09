using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class quit : MonoBehaviour
{
    public Button button;
    void Start()
    {
        Button btn = button.GetComponent<Button>();
        btn.onClick.AddListener(OnClick);
    }

    void OnClick()
    {
        Application.Quit();
    }
}
