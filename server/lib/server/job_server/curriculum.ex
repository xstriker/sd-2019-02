defmodule Server.JobServer.Curriculum do
  use Ecto.Schema
  import Ecto.Changeset

  @derive {Jason.Encoder, only: [:area, :contato, :nome, :salario, :tempo]}

  schema "curriculuns" do
    field :area, :string
    field :contato, :string
    field :nome, :string
    field :salario, :integer
    field :tempo, :integer

    timestamps()
  end

  @doc false
  def changeset(curriculum, attrs) do
    curriculum
    |> cast(attrs, [:nome, :contato, :area, :tempo, :salario])
    |> validate_required([:nome, :contato, :area, :tempo, :salario])
  end
end
